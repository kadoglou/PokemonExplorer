import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Provides a simple way to observe the device's internet connectivity status.
 *
 * Implementations expose [isConnected] as a [Flow] of Boolean values,
 * where `true` means the device has internet access and `false` means it doesn't.
 */
interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
}

class AndroidConnectivityObserver(
    context: Context,
) : ConnectivityObserver {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()

    override val isConnected: Flow<Boolean>
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        get() = callbackFlow {
            if (connectivityManager == null) {
                trySend(false)
                close()
                return@callbackFlow
            }

            fun currentStatus(): Boolean {
                val network = connectivityManager.activeNetwork ?: return false
                val caps = connectivityManager.getNetworkCapabilities(network) ?: return false
                return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }

            trySend(currentStatus())

            val callback = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    trySend(true)
                }

                override fun onLost(network: Network) {
                    trySend(false)
                }

                override fun onUnavailable() {
                    trySend(false)
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose { runCatching { connectivityManager.unregisterNetworkCallback(callback) } }
        }
}