data class UiError(val title: String, val message: String)

fun Result.Error.toUi(): UiError = when (this) {
    Result.Error.Remote.NotFound ->
        UiError("Not found", "We couldn’t find this Pokémon. Try another name?")

    Result.Error.Remote.Unavailable ->
        UiError("Network issue", "Check your connection and try again.")

    Result.Error.Local.CouldNotLoad ->
        UiError("Load failed", "Couldn’t load from storage.")

    Result.Error.Local.CouldNotSave ->
        UiError("Save failed", "We couldn’t save your changes.")

    Result.Error.Unknown ->
        UiError("Unexpected error", "An unknown error occurred.")

    is Result.Error.UnknownError ->
        UiError("Unexpected error", "An unknown business logic error occurred.")
}