package dev.byto.moly.ui

data class UiState(
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isError: Boolean,
    val errorText: String? = null,
    val networkError: Boolean
) {
    companion object {
        fun loadingState(isInitial: Boolean = true): UiState = UiState(
            isLoading = true,
            isSuccess = !isInitial,
            isError = false,
            networkError = false
        )

        fun successState(): UiState = UiState(
            isLoading = false,
            isSuccess = true,
            isError = false,
            networkError = false
        )

        fun errorState(
            isInitial: Boolean = true, errorText: String
        ): UiState = UiState(
            isLoading = false,
            isSuccess = !isInitial,
            isError = true,
            errorText = errorText,
            networkError = true
        )
    }
}