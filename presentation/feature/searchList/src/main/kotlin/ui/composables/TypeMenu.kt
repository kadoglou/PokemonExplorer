package ui.composables

import PokemonType
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import badge.TypeBadgeButton
import chip.ChipTitle

@Composable
internal fun TypeMenu(
    isTypeMenuVisible: Boolean,
    onDismiss: () -> Unit,
    onTypeSelected: (PokemonType) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val alpha by animateFloatAsState(
        targetValue = if (isTypeMenuVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
    )
    if (alpha != 0f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 30.dp)
                .alpha(alpha)
                .clickable(
                    enabled = isTypeMenuVisible,
                    indication = null,
                    interactionSource = interactionSource
                ) { onDismiss() },
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            PokemonType.entries.chunked(2)
                .forEach { rowTypes ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowTypes.forEach { type ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TypeBadgeButton(type = type, size = 60.dp) {
                                    onTypeSelected(type)
                                }
                                ChipTitle(
                                    modifier = Modifier.offset(y = (-10).dp),
                                    title = type.apiName.replaceFirstChar { it.uppercaseChar() },
                                    textSize = 14.sp,
                                    textModifier = Modifier.padding(
                                        horizontal = 20.dp,
                                    )
                                )
                            }
                        }
                    }
                }
        }
    }
}