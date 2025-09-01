package dev.kadoglou.pokemonexplorer

import ConnectivityObserver
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.kadoglou.pokemonexplorer.composables.ConnectivityBanner
import dev.kadoglou.pokemonexplorer.ui.theme.PokemonExplorerTheme
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    @OptIn(FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val connectivityObserver: ConnectivityObserver = get()
            val rootNavController = rememberNavController()
            PokemonExplorerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(modifier = Modifier.animateContentSize()) {
                        ConnectivityBanner(
                            connectivityObserver = connectivityObserver,
                            innerPadding = innerPadding
                        )
                        Box(Modifier.fillMaxSize()) {
                            AppRootNavigation(rootNavController)
                        }
                    }
                }
            }
        }
    }
}