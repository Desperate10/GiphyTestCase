package desperate.giphytestcase.presentation.screens.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import desperate.giphytestcase.databinding.FragmentTrendingBinding
import desperate.giphytestcase.presentation.model.GifView
import desperate.giphytestcase.presentation.model.UiState
import desperate.giphytestcase.presentation.screens.trending.adapter.TrendingAdapter
import desperate.giphytestcase.utils.SearchViewQueryTextCallback
import desperate.giphytestcase.utils.autoCleaned
import desperate.giphytestcase.utils.collectLifecycleFlow
import desperate.giphytestcase.utils.setupQueryTextSubmit

@AndroidEntryPoint
class TrendingFragment : Fragment(),
    TrendingAdapter.OnGifClickListener {

    private var binding: FragmentTrendingBinding by autoCleaned()
    private var adapter: TrendingAdapter by autoCleaned()
    private val viewModel by viewModels<TrendingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindAdapter()
        observeViewModel()
        setListeners()
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
        setupSubmitSearchListener()
        onRemoveTextListener()
    }

    private fun bindAdapter() {
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = TrendingAdapter()
        binding.recyclerView.adapter = adapter
        linearLayoutManager.isSmoothScrollbarEnabled = true
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter.onGifClickListener = this
    }

    private fun observeViewModel() {
        collectLifecycleFlow(viewModel.gifs) { gifs ->
            adapter.submitData(gifs)
        }

        collectLifecycleFlow(adapter.loadStateFlow) { loadState ->
            when (loadState.refresh) {
                is LoadState.NotLoading -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                LoadState.Loading -> {

                }

                is LoadState.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun setupSubmitSearchListener() {
        binding.searchView.setupQueryTextSubmit(object : SearchViewQueryTextCallback {
            override fun onQueryTextSubmit(query: String?) {
                query?.let { viewModel.searchGifsByQuery(it) }
            }
        })
    }

    override fun onClick(position: Int) {
        when (viewModel.state.value) {
            UiState.TrendingMode -> {
                findNavController().navigate(
                    TrendingFragmentDirections.actionTrendingFragmentToFullScreenFragment(
                        position,
                        ""
                    )
                )
            }
            UiState.SearchMode -> {
                findNavController().navigate(
                    TrendingFragmentDirections.actionTrendingFragmentToFullScreenFragment(
                        position,
                        binding.searchView.query.toString()
                    )
                )
            }
        }
    }

    override fun onLongCLick(gif: GifView) {
        viewModel.deleteGif(gif)
    }

    private fun onRemoveTextListener() {
        val clearButton =
            binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        clearButton.setOnClickListener {
            binding.searchView.setQuery("", false)
            viewModel.getTrendingGifs()
        }
    }


}