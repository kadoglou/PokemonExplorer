package ui

import RetroErrorSnackbar
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.kadoglou.presentation.resources.R
import fancyCard.FancyPokemonCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import loadingBall.LoadingBall
import logic.SearchListAction
import logic.SearchListState
import ui.composables.BlurBackground
import ui.composables.GradientBackgroundBox
import ui.composables.Header
import ui.composables.SearchBar
import ui.composables.TypeMenu
import ui.composables.animatedTypeGradient


@SuppressLint("FrequentlyChangingValue")
@Composable
internal fun SearchListScreen(
    state: SearchListState,
    onAction: (SearchListAction) -> Unit,
) {
    if (state.selectedType == null) return
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()


    // Debounced/Delayed loading flag to avoid flashing spinner on instant responses
    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(state.isLoading) {
        if (state.isLoading) {
            // Wait a bit; only show spinner if still loading after the delay
            delay(50)
            if (state.isLoading) showLoading = true
        } else {
            // Loading finished: hide immediately
            showLoading = false
        }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pokeball))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = showLoading,
        restartOnPlay = true
    )

    // Trigger when user scrolls close to the bottom
    LaunchedEffect(state.query, state.selectedType, state.pokemons) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisible >= state.pokemons.lastIndex || state.currentPage == 0
        }.distinctUntilChanged().collect { nearEnd ->
            if (nearEnd) onAction(SearchListAction.LoadNextPage)
        }
    }

    // region Screen

    BlurBackground(
        active = state.isTypeMenuVisible,
        onDismiss = { onAction(SearchListAction.OnTypeMenuDismiss) }) {

        GradientBackgroundBox(
            modifier = Modifier
                .animatedTypeGradient(state.selectedType)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    focusManager.clearFocus()
                }) {

            val isHeaderVisible = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index == 0

            LazyColumn(
                state = listState,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            ) {

                item {
                    Header(
                        modifier = Modifier.align(Alignment.Center),
                        type = state.selectedType,
                        composition = composition,
                        progress = progress,
                        isLoading = showLoading
                    ) {
                        focusManager.clearFocus()
                        onAction(SearchListAction.OnTypeBadgeClick)
                    }
                    Spacer(Modifier.height(20.dp))
                }

                item {
                    SearchBar(
                        value = state.query,
                        onValueChange = { onAction(SearchListAction.OnSearchQueryChange(it)) })
                    Spacer(Modifier.height(30.dp))
                }

                items(state.pokemons) { pokemon ->
                    FancyPokemonCard(
                        name = pokemon.name,
                        hp = pokemon.hp,
                        attack = pokemon.attack,
                        defense = pokemon.defense,
                        imageUrl = pokemon.imageUrl
                    )
                    Spacer(Modifier.height(20.dp))
                }

                // Empty List Image
                if (state.query.isNotBlank() && state.pokemons.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = R.drawable.empty_search),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                // Loading Indicator
                if (showLoading && !isHeaderVisible) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            LoadingBall(
                                modifier = Modifier.align(Alignment.Center),
                                composition,
                                progress
                            )
                        }
                    }
                }
            }
        }
    }

    TypeMenu(
        state.isTypeMenuVisible, onDismiss = { onAction(SearchListAction.OnTypeMenuDismiss) }) {
        onAction(SearchListAction.OnTypeSelected(it))
        onAction(SearchListAction.OnTypeMenuDismiss)
    }

    RetroErrorSnackbar(
        errorMessage = state.error?.message,
        onDismissed = { onAction(SearchListAction.ClearError) }
    )

    // endregion
}