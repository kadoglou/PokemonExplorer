package ui.composables

import PokemonType
import PokemonTypeTheme
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

@Composable
internal fun Modifier.animatedTypeGradient(type: PokemonType): Modifier {
    val transition = updateTransition(targetState = type, label = "typeTransition")

    val bl by transition.animateColor(
        transitionSpec = { tween(1200, easing = FastOutSlowInEasing) },
        label = "bl"
    ) { t -> PokemonTypeTheme.entries.first { it.type == t }.brushBL }

    val tr by transition.animateColor(
        transitionSpec = { tween(1800, easing = FastOutLinearInEasing) },
        label = "tr"
    ) { t -> PokemonTypeTheme.entries.first { it.type == t }.brushTR }

    return this.drawBehind {
        val start = Offset(0f, size.height)
        val end = Offset(size.width, 0f)
        drawRect(Brush.linearGradient(listOf(bl, tr), start = start, end = end))
    }
}