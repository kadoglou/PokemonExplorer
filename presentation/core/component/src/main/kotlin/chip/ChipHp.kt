package chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import dev.kadoglou.presentation.resources.R

@Composable
fun ChipHp(
    stat: String,
) {
    Chip(
        backgroundColor = Color(0xFF33CE65),
        icon = painterResource(R.drawable.hp_icon),
        text = stat
    )
}
