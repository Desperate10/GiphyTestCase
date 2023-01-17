package desperate.giphytestcase.presentation.screens.fullscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import desperate.giphytestcase.domain.repository.GiphyRepository
import javax.inject.Inject

@HiltViewModel
class FullScreenViewModel @Inject constructor(
    repository: GiphyRepository
) : ViewModel() {

    val gifs = repository.getTrending()

}