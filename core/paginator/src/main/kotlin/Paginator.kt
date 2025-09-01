class Paginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (currentKey: Key, isLoading: Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Result<Item>,
    private val getNextKey: suspend (currentKey: Key, result: Item) -> Key,
    private val onError: suspend (Result.Error?) -> Unit,
    private val onSuccess: suspend (result: Item, newKey: Key) -> Unit,
    private val endReached: suspend (currentKey: Key, result: Item) -> Boolean
) {
    private var currentKey = initialKey
    private var isLoading = false
    private var isEndReached = false

    suspend fun loadNextItems() {
        if (isLoading || isEndReached) {
            return
        }

        isLoading = true
        onLoadUpdated(currentKey, true)

        val result = onRequest(currentKey)

        isLoading = false

        val item = result
        if (item is Result.Error) {
            item.onError { err ->
                onError(err)
                onLoadUpdated(currentKey, false)
                return
            }
        } else {
            item.onSuccess { data ->
                currentKey = getNextKey(currentKey, data)

                onSuccess(data, currentKey)

                onLoadUpdated(currentKey, false)

                isEndReached = endReached(currentKey, data)
            }
        }
    }

    fun reset() {
        currentKey = initialKey
        isEndReached = false
    }
}