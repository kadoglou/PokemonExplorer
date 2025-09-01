package dev.kadoglou.pokemonexplorer.composables

import ConnectivityObserver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import dev.kadoglou.presentation.resources.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

internal enum class BannerState { Connected, Disconnected }

@OptIn(FlowPreview::class)
@Composable
internal fun ConnectivityBanner(
    connectivityObserver: ConnectivityObserver,
    innerPadding: PaddingValues
) {
    var showBanner by remember { mutableStateOf(false) }
    var bannerState by remember { mutableStateOf(BannerState.Connected) }
    var hasShownDisconnected by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            connectivityObserver.isConnected
                .distinctUntilChanged()
                .debounce(300)
                .collect { status ->
                    if (!status) {
                        bannerState = BannerState.Disconnected
                        showBanner = true
                        hasShownDisconnected = true
                    } else {
                        if (hasShownDisconnected) {
                            bannerState = BannerState.Connected
                            showBanner = true
                            delay(2000)
                            showBanner = false
                            hasShownDisconnected = false
                        }
                    }
                }
        }
    }

    AnimatedVisibility(
        visible = showBanner,
        enter = expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(250)
        ) + fadeIn(animationSpec = tween(150)),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(200)
        ) + fadeOut(animationSpec = tween(150))
    ) {
        val backgroundColor =
            if (bannerState == BannerState.Disconnected) Color(0xFFB00020) else Color(0xFF2E7D32)
        val message =
            if (bannerState == BannerState.Disconnected) "No internet connection" else "Connection restored"
        val image =
            if (bannerState == BannerState.Disconnected) R.drawable.no_internet else R.drawable.connected

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(top = innerPadding.calculateTopPadding())
                .height(60.dp)
        ) {
            Row(
                Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 10.dp)
                )
            }
        }
    }
}