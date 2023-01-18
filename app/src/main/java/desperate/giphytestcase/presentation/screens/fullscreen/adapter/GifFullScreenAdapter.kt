package desperate.giphytestcase.presentation.screens.fullscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import desperate.giphytestcase.R
import desperate.giphytestcase.databinding.FullscreenItemBinding
import desperate.giphytestcase.presentation.model.GifView

class GifFullScreenAdapter: PagingDataAdapter<GifView, GIfFullScreenViewHolder>(
    GifFullScreenDiffUtil
) {

    override fun onBindViewHolder(holder: GIfFullScreenViewHolder, position: Int) {
        val gifItem = getItem(position)!!
        with(holder.binding) {
            gifItem.also {
                Glide.with(gifImage)
                    .load(it.url)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(gifImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GIfFullScreenViewHolder {
        val binding = FullscreenItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GIfFullScreenViewHolder(binding)
    }
}