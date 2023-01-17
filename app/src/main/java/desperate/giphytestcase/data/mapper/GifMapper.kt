package desperate.giphytestcase.data.mapper

import desperate.giphytestcase.data.local.entity.GifDbModel
import desperate.giphytestcase.domain.model.Gif
import desperate.giphytestcase.presentation.model.GifView

fun mapDtModelToDbModel(gif: Gif) : GifDbModel {
    return GifDbModel(
        id = gif.id,
        url = gif.images,
        isDeleted = false
    )
}

fun mapDbModelToDtModel(gif: GifDbModel) : Gif {
    return Gif(
        id = gif.id,
        images = gif.url
    )
}

fun mapDomainModelToView(gif: Gif) : GifView {
    return GifView(
        id = gif.id,
        url = gif.images.downsized.url
    )
}