/**
 * Transform a successful value while preserving any [Error].
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> this
}

/**
 * Chain another result-returning operation if this is [Result.Success];
 * propagates [Error] unchanged.
 */
inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> = when (this) {
    is Result.Success -> transform(data)
    is Result.Error -> this
}

/** Run [block] only when this is [Result.Success]; returns the original [Result]. */
inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> = apply {
    if (this is Result.Success) block(data)
}

/** Run [block] only when this is [Result.Error]; returns the original [Result]. */
inline fun <T> Result<T>.onError(block: (Result.Error) -> Unit): Result<T> = apply {
    if (this is Result.Error) block(this)
}

/** Extract the value or return `null` when this is [Error]. */
fun <T> Result<T>.getOrNull(): T? = (this as? Result.Success)?.data

/** Unwrap [Result.Success] or throw [DomainFailure] with the contained [Result.Error]. */
fun <T> Result<T>.getOrThrow(): T = when (this) {
    is Result.Success -> data
    is Result.Error -> throw DomainFailure(this)
}

/** Return [Result.Success] or throw if this result is not successful. */
fun <T> Result<T>.successOrThrow(): Result.Success<T> =
    this as? Result.Success<T>
        ?: error("Expected Result.Success but was $this")
