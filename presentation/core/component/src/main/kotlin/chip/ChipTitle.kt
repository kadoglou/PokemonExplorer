package chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ChipTitle(
    modifier: Modifier = Modifier,
    title: String,
    textSize: TextUnit = 18.sp,
    textModifier: Modifier = Modifier,
) {
    Chip(
        modifier = modifier,
        backgroundColor = Color(0xFF151515),
        text = title,
        textFontWeight = FontWeight.Normal,
        textSize = textSize,
        textModifier = textModifier

    )
}