package desperate.giphytestcase.data.mapper

import desperate.giphytestcase.data.local.entity.GifDbModel
import desperate.giphytestcase.domain.model.Gif

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