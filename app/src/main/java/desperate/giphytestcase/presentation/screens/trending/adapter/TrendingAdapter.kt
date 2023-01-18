package desperate.giphytestcase.presentation.screens.trending.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import desperate.giphytestcase.R
import desperate.giphytestcase.databinding.TrendingItemBinding
import desperate.giphytestcase.presentation.model.GifView

class TrendingAdapter : PagingDataAdapter<GifView, TrendingViewHolder>(TrendingDIffUtil) {

    var onGifClickListener: OnGifClickListener? = null

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val gifItem = getItem(position)
        with(holder.binding) {
            gifItem.also {
                Glide.with(gif)
                    .load(it?.url)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(gif)
            }

            root.setOnClickListener {
                onGifClickListener?.onClick(position)
            }
            root.setOnLongClickListener {
                gifItem?.let { gif -> onGifClickListener?.onLongCLick(gif) }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding = TrendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingViewHolder(binding)
    }

    interface OnGifClickListener {
        fun onClick(position: Int)

        fun onLongCLick(gif: GifView)
    }
}