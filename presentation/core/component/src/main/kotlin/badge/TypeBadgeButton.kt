package badge

import PokemonType
import PokemonTypeTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.kadoglou.presentation.resources.R
import loadingBall.LoadingBall

@Composable
fun TypeBadgeButton(
    modifier: Modifier = Modifier,
    type: PokemonType,
    size: Dp = 80.dp,
    onClick: () -> Unit = {},
) {
    val theme = PokemonTypeTheme.entries.first { it.type == type }

    Box(modifier = modifier.wrapContentSize()) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(50.dp))
                .size(size * 1.8f)
                .align(Alignment.Center)
                .blur(15.dp)
                .offset(x = 0.dp, y = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(size)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.Black)
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White)
                .size(size)
                .padding(3.dp)
                .clickable { onClick() }
                .align(Alignment.Center)

        ) {
            Image(
                painter = painterResource(id = theme.iconRes),
                contentDescription = "${theme.type} icon",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun Preview_TypeBadgeButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        TypeBadgeButton(type = PokemonType.Fire) {}
    }
}