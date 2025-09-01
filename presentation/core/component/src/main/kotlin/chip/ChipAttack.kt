package chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import dev.kadoglou.presentation.resources.R

@Composable
fun ChipAttack(
    stat: String,
) {
    Chip(
        backgroundColor = Color(0xFFFB5858),
        icon = painterResource(R.drawable.attack_icon),
        text = stat
    )
}