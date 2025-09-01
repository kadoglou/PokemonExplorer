package ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun BlurBackground(
    modifier: Modifier = Modifier,
    active: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val radius by animateDpAsState(
        targetValue = if (active) 20.dp else 0.dp,
        animationSpec = tween(durationMillis = 500),
    )

    val alpha by animateFloatAsState(
        targetValue = if (active) 0.4f else 0f,
        animationSpec = tween(durationMillis = 500),
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .blur(radius)
            .clickable(enabled = active) { onDismiss() }
    ) {
        content()
        if (alpha != 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = alpha))
            )
        }
    }
}