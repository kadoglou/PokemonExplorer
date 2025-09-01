package ui.composables

import PokemonType
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import badge.TypeBadgeButton
import chip.ChipTitle
import com.airbnb.lottie.LottieComposition
import loadingBall.LoadingBall

@Composable
internal fun Header(
    modifier: Modifier = Modifier,
    type: PokemonType,
    composition: LottieComposition?,
    progress: Float,
    isLoading: Boolean,
    onAction: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            TypeBadgeButton(type = type) {
                onAction()
            }
            if (isLoading) {
                LoadingBall(
                    composition = composition,
                    progress = progress
                )
            }
        }
        ChipTitle(
            modifier = Modifier.offset(y = (-10).dp),
            title = type.apiName.replaceFirstChar { it.uppercaseChar() },
            textModifier = Modifier.padding(horizontal = 30.dp, vertical = 2.dp)
        )
    }
}