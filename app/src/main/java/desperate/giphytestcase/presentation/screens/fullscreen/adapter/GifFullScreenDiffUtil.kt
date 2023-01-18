package desperate.giphytestcase.presentation.screens.fullscreen.adapter

import androidx.recyclerview.widget.DiffUtil
import desperate.giphytestcase.presentation.model.GifView

object GifFullScreenDiffUtil  : DiffUtil.ItemCallback<GifView>() {

    override fun areItemsTheSame(oldItem: GifView, newItem: GifView): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GifView, newItem: GifView): Boolean {
        return oldItem == newItem
    }

}