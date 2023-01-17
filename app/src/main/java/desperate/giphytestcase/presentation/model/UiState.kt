package desperate.giphytestcase.presentation.model

sealed class UiState {
    object TrendingMode: UiState()
    object SearchMode: UiState()
}
