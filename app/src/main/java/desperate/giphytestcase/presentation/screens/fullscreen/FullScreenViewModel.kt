package desperate.giphytestcase.presentation.screens.fullscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import desperate.giphytestcase.domain.repository.GiphyRepository
import javax.inject.Inject

@HiltViewModel
class FullScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: GiphyRepository
) : ViewModel() {

    private val args by lazy { FullScreenFragmentArgs.fromSavedStateHandle(savedStateHandle) }

    val gifs = if (args.searchText.isEmpty()) {
        repository.getTrending()
    } else {
        repository.search(args.searchText)
    }

}