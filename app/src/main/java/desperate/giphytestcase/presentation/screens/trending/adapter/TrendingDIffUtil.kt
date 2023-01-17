package desperate.giphytestcase.presentation.screens.trending.adapter

import androidx.recyclerview.widget.DiffUtil
import desperate.giphytestcase.presentation.model.GifView

object TrendingDIffUtil : DiffUtil.ItemCallback<GifView>(){

    override fun areItemsTheSame(oldItem: GifView, newItem: GifView): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GifView, newItem: GifView): Boolean {
        return oldItem == newItem
    }
}