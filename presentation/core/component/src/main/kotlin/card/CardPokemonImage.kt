package card

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.kadoglou.presentation.resources.R

@Composable
internal fun RowScope.PokemonImage(
    imageUrl: String
) {
    val context = LocalContext.current
    val model = imageUrl.takeIf { it.isNotBlank() }
    val request = ImageRequest.Builder(context)
        .data(model)
        .crossfade(true)
        .build()
    AsyncImage(
        model = request,
        contentDescription = "artwork",
        placeholder = painterResource(R.drawable.question_mark),
        error = painterResource(R.drawable.question_mark),       // load failed
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 10.dp)
            .size(80.dp),
    )
}