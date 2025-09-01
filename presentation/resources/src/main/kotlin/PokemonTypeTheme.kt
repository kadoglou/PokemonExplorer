import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import dev.kadoglou.presentation.resources.R

enum class PokemonTypeTheme(
    val brushBL: Color,
    val brushTR: Color,
    val type: PokemonType,
    @DrawableRes val iconRes: Int
) {
    Dark(
        brushBL = Color(0xFF9D77E6),
        brushTR = Color(0xFF330258),
        type = PokemonType.Dark,
        iconRes = R.drawable.dark
    ),
    Dragon(
        brushBL = Color(0xFF98C3F5),
        brushTR = Color(0xFF002F80),
        type = PokemonType.Dragon,
        iconRes = R.drawable.dragon
    ),
    Electric(
        brushBL = Color(0xFFF8E22A),
        brushTR = Color(0xFFD28E00),
        type = PokemonType.Electric,
        iconRes = R.drawable.electric
    ),
    Fairy(
        brushBL = Color(0xFFDF79FE),
        brushTR = Color(0xFF9C0C52),
        type = PokemonType.Fairy,
        iconRes = R.drawable.fairy
    ),
    Fire(
        brushBL = Color(0xFFF87001),
        brushTR = Color(0xFFA20E00),
        type = PokemonType.Fire,
        iconRes = R.drawable.fire
    ),
    Ghost(
        brushBL = Color(0xFF7B56DE),
        brushTR = Color(0xFF04186F),
        type = PokemonType.Ghost,
        iconRes = R.drawable.ghost
    ),
    Grass(
        brushBL = Color(0xFF50DC77),
        brushTR = Color(0xFF077A2B),
        type = PokemonType.Grass,
        iconRes = R.drawable.grass
    ),
    Psychic(
        brushBL = Color(0xFFFD6E9A),
        brushTR = Color(0xFFA1030C),
        type = PokemonType.Psychic,
        iconRes = R.drawable.psychic
    ),
    Steel(
        brushBL = Color(0xFF69C8DC),
        brushTR = Color(0xFF093F64),
        type = PokemonType.Steel,
        iconRes = R.drawable.steel
    ),
    Water(
        brushBL = Color(0xFF42D2F8),
        brushTR = Color(0xFF024D97),
        type = PokemonType.Water,
        iconRes = R.drawable.water
    );
}

@Composable
fun PokemonTypeTheme.iconPainter(): Painter = painterResource(id = iconRes)