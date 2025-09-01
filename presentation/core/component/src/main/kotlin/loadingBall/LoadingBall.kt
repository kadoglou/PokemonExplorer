package loadingBall

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation

@Composable
fun LoadingBall(
    modifier: Modifier = Modifier,
    composition: LottieComposition?,
    progress: Float,
) {
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
            .size(80.dp)
    )
}