package desperate.giphytestcase.data.remote.model

import desperate.giphytestcase.domain.model.Gif

data class GiphyResponse(
    val data: List<Gif>
)