package chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    icon: Painter? = null,
    iconSize: Dp = 16.dp,
    iconColor: Color = Color.White,
    text: String,
    textSize: TextUnit = 16.sp,
    textFontWeight: FontWeight = FontWeight.Medium,
    textModifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .wrapContentWidth()
            .wrapContentHeight()
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = iconColor
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            modifier = textModifier,
            text = text,
            fontSize = textSize,
            fontWeight = textFontWeight,
            color = Color.White
        )
    }
}