package desperate.giphytestcase.presentation.screens.fullscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import desperate.giphytestcase.data.mapper.mapDomainModelToView
import desperate.giphytestcase.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FullScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: GiphyRepository
) : ViewModel() {

    private val args by lazy { FullScreenFragmentArgs.fromSavedStateHandle(savedStateHandle) }

    val gifs = if (args.searchText.isEmpty()) {
        repository.getTrending().map { pagingData->
            pagingData.map {
                mapDomainModelToView(it)
            }
        }
    } else {
        repository.search(args.searchText).map { pagingData ->
            pagingData.map {
                mapDomainModelToView(it)
            }
        }
    }

}