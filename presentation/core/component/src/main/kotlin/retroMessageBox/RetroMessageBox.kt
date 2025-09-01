package retroMessageBox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kadoglou.presentation.resources.R

@Composable
fun RetroMessageBox(
    message: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        CustomBorderBox(
            modifier = Modifier
                .padding(3.dp)
                .align(Alignment.Center)
                .background(Color.White)
        ) {
            // Inner content container with double borders
            CustomBorderBox(
                topBorder = 4.dp,
                bottomBorder = 3.dp,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 6.dp)
                    .align(Alignment.Center)
                    .background(Color.White)
            ) {
                Text(
                    text = message,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    softWrap = true,
                    maxLines = 3
                )
            }


        }
        // Top-left pokéball
        Image(
            painter = painterResource(id = R.drawable.pokeball_small),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.TopStart)
        )

        // Top-right pokéball
        Image(
            painter = painterResource(id = R.drawable.pokeball_small),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.TopEnd)
        )

        // Bottom-left pokéball
        Image(
            painter = painterResource(id = R.drawable.pokeball_small),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.BottomStart)
        )

        // Bottom-right pokéball
        Image(
            painter = painterResource(id = R.drawable.pokeball_small),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun CustomBorderBox(
    modifier: Modifier = Modifier,
    topBorder: Dp = 3.dp,
    bottomBorder: Dp = 4.dp,
    startBorder: Dp = 3.dp,
    endBorder: Dp = 3.dp,
    borderColor: Color = Color.Black,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .drawBehind {
                val strokePaint = Paint().apply { color = borderColor }

                // Top border
                drawRect(
                    color = borderColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, topBorder.toPx())
                )

                // Bottom border
                drawRect(
                    color = borderColor,
                    topLeft = Offset(0f, size.height - bottomBorder.toPx()),
                    size = Size(size.width, bottomBorder.toPx())
                )

                // Start (left) border
                drawRect(
                    color = borderColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(startBorder.toPx(), size.height)
                )

                // End (right) border
                drawRect(
                    color = borderColor,
                    topLeft = Offset(size.width - endBorder.toPx(), 0f),
                    size = Size(endBorder.toPx(), size.height)
                )
            }
    ) {
        content()
    }
}