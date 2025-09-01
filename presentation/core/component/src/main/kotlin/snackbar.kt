import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import retroMessageBox.RetroMessageBox

@Composable
fun RetroErrorSnackbar(
    errorMessage: String?,
    onDismissed: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = errorMessage,
                    withDismissAction = true
                )
                if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                    onDismissed()
                }
            }
        }
    }

    if (errorMessage != null) {
        Box(Modifier.Companion.fillMaxSize()) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.Companion
                    .align(Alignment.Companion.BottomCenter)
                    .padding(16.dp)
            ) { message ->
                RetroMessageBox(
                    message = message.visuals.message,
                )
            }
        }
    }
}