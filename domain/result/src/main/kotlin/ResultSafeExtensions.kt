import android.database.sqlite.SQLiteException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CancellationException
import java.io.IOException
import java.net.UnknownHostException

/** Execute a suspending remote [block] and map common network exceptions to [Result.Error.Remote]. */
suspend inline fun <T> safeCallRemote(crossinline block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: ClientRequestException) {
        if (e.response.status.value == 404) Result.Error.Remote.NotFound
        else Result.Error.Unknown
    } catch (_: ServerResponseException) {
        Result.Error.Remote.Unavailable
    } catch (_: HttpRequestTimeoutException) {
        Result.Error.Remote.Unavailable
    } catch (_: UnknownHostException) {
        Result.Error.Remote.Unavailable
    } catch (_: IOException) {
        Result.Error.Remote.Unavailable
    } catch (_: Exception) {
        Result.Error.Unknown
    }
}

/** Execute a local read [block], mapping DB/IO failures to [Result.Error.Local.CouldNotLoad]. */
suspend inline fun <T> safeReadLocal(
    crossinline block: suspend () -> T,
): Result<T> {
    return try {
        Result.Success(block())
    } catch (_: SQLiteException) {
        Result.Error.Local.CouldNotLoad
    } catch (_: IOException) {
        Result.Error.Local.CouldNotLoad
    } catch (_: Exception) {
        Result.Error.Unknown
    }
}

/** Execute a local write [block], mapping DB/IO failures to [Result.Error.Local.CouldNotSave]. */
suspend inline fun <T> safeWriteLocal(
    crossinline block: suspend () -> T,
): Result<T> {
    return try {
        Result.Success(block())
    } catch (_: SQLiteException) {
        Result.Error.Local.CouldNotSave
    } catch (_: IOException) {
        Result.Error.Local.CouldNotSave
    } catch (_: Exception) {
        Result.Error.Unknown
    }
}

/**
 * Business-level guard: run [block] and return success or convert thrown errors to [Result.Error].
 * - Rethrows [CancellationException].
 * - Converts [DomainFailure] to its carried error.
 * - Any other throwable becomes [Result.Error.Unknown].
 */
suspend inline fun <T> safeWrap(crossinline block: suspend () -> T): Result<T> =
    try {
        Result.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: DomainFailure) {
        e.error
    } catch (_: Throwable) {
        Result.Error.Unknown
    }
