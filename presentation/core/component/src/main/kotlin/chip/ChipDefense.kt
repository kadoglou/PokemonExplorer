package chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import dev.kadoglou.presentation.resources.R

@Composable
fun ChipDefense(
    stat: String,
) {
    Chip(
        backgroundColor = Color(0xFF4292F9),
        icon = painterResource(R.drawable.defense_icon),
        text = stat
    )
}
