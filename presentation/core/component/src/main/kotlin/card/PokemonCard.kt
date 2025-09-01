package card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chip.ChipAttack
import chip.ChipDefense
import chip.ChipHp

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    name: String,
    hp: Int,
    attack: Int,
    defense: Int,
    imageUrl: String,
    height: Dp = 100.dp,
    cornerRadius: Dp = 18.dp,
    backgroundColor: Color = Color.White
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            PokemonName(name)
            PokemonDetails(
                hp,
                attack,
                defense
            )
        }
        PokemonImage(imageUrl)
    }
}

@Composable
private fun ColumnScope.PokemonName(
    name: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .weight(2f)
            .fillMaxHeight()
    ) {
        Text(
            text = name.replaceFirstChar { it.uppercaseChar() },
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}

@Composable
private fun ColumnScope.PokemonDetails(
    hp: Int,
    attack: Int,
    defense: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .weight(3f)
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChipHp(hp.toString())
        ChipAttack(attack.toString())
        ChipDefense(defense.toString())
    }
}