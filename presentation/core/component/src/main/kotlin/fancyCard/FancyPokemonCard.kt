package fancyCard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kadoglou.presentation.resources.R
import kotlin.math.tan

private data class StatSlice(
    val value: Int,
    val color: Color,
    val icon: Painter,
    val contentColor: Color = Color.White
)

@Composable
fun FancyPokemonCard(
    modifier: Modifier = Modifier,
    name: String,
    hp: Int,
    attack: Int,
    defense: Int,
    imageUrl: String,
    height: Dp = 100.dp,
    cornerRadius: Dp = 18.dp,
    backgroundColor: Color = Color.White,
    headerHeight: Dp = 28.dp,
) {
    val stats = listOf(
        StatSlice(hp, Color(0xFF2ECC71), painterResource(R.drawable.hp_icon)),
        StatSlice(attack, Color(0xFFFF6B6B), painterResource(R.drawable.attack_icon)),
        StatSlice(defense, Color(0xFF4DA3FF), painterResource(R.drawable.defense_icon)),
    )

    Box(
        modifier = modifier
            .height(height)
            .graphicsLayer {
                shadowElevation = 14.dp.toPx()
                shape = RoundedCornerShape(cornerRadius)
                clip = false
                ambientShadowColor = Color.Black.copy(alpha = 0.90f)
                spotShadowColor = Color.Black.copy(alpha = 0.90f)
            }
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
    ) {
        Column(Modifier.fillMaxWidth()) {
            TitleBar(name = name, height = headerHeight)
            Body(
                imageUrl = imageUrl,
                stats = stats,
                cornerRadius = cornerRadius,
                imagePercent = 0.32f,
                angleDeg = 12f
            )
        }
    }
}

@Composable
private fun TitleBar(name: String, height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color(0xFF141414)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.replaceFirstChar { it.uppercaseChar() },
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun Body(
    imageUrl: String,
    stats: List<StatSlice>,
    cornerRadius: Dp,
    imagePercent: Float,
    angleDeg: Float
) {
    val imageWeight = imagePercent
    val statsWeight = 1f - imagePercent

    val context = LocalContext.current
    val model = imageUrl.takeIf { it.isNotBlank() }
    val request = ImageRequest.Builder(context)
        .data(model)
        .crossfade(250)
        .build()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(imageWeight)
                .fillMaxHeight()
                .padding(start = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            AsyncImage(
                model = request,
                placeholder = painterResource(R.drawable.question_mark),
                error = painterResource(R.drawable.question_mark),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight()
            )
        }
        StatsArea(
            stats = stats,
            modifier = Modifier
                .weight(statsWeight)
                .fillMaxHeight(),
            angleDeg = angleDeg
        )
    }
}

@Composable
private fun StatsArea(
    stats: List<StatSlice>,
    modifier: Modifier,
    angleDeg: Float
) {
    Box(modifier) {
        Canvas(Modifier.matchParentSize()) {
            val n = stats.size
            if (n == 0) return@Canvas

            val w = size.width
            val h = size.height
            val base = w / n

            val dx = (tan(Math.toRadians(angleDeg.toDouble())) * h).toFloat()

            fun bottomX(i: Int) = when (i) {
                0 -> 0f
                n -> w
                else -> i * base
            }

            fun topX(i: Int) = when (i) {
                0 -> dx
                n -> w
                else -> i * base + dx
            }

            for (i in 0 until n) {
                val path = Path().apply {
                    moveTo(topX(i), 0f)
                    lineTo(topX(i + 1), 0f)
                    lineTo(bottomX(i + 1), h)
                    lineTo(bottomX(i), h)
                    close()
                }
                drawPath(path, color = stats[i].color)
            }
        }

        Row(
            Modifier
                .fillMaxSize()
                .padding(start = angleDeg.dp)
        ) {
            stats.forEach { s ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = s.icon,
                        contentDescription = null,
                        tint = s.contentColor,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = s.value.toString(),
                        color = s.contentColor,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}