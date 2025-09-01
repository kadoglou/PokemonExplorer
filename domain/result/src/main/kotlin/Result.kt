/**
 * Lightweight, domain-first result type used across layers.
 * Use [Success] to carry data and [Error] to describe failures without throwing.
 */
sealed class Result<out T> {

    /** Success case holding the computed [data]. */
    data class Success<T>(val data: T) : Result<T>()

    /**
     * Failure hierarchy.
     *
     * Prefer specific errors (e.g. [Error.Remote.NotFound]) over [Error.Unknown].
     */
    sealed class Error : Result<Nothing>() {

        /** Errors that originated from a remote call (network / server). */
        sealed class Remote : Error() {
            data object NotFound : Remote()
            data object Unavailable : Remote()
        }

        /** Errors that originated from local storage (DB / filesystem). */
        sealed class Local : Error() {
            data object CouldNotLoad : Local()
            data object CouldNotSave : Local()

        }

        /** Optional rich error carrying a human-readable [reason]. */
        data class UnknownError(val reason: String) : Error()

        /** Fallback when no better classification is available. */
        data object Unknown : Error()
    }
}

/** Lightweight throwable carrying a [Result.Error]; stacktrace suppressed. */
class DomainFailure(val error: Result.Error) : RuntimeException() {
    override fun fillInStackTrace(): Throwable = this
}

