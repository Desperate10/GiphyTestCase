package desperate.giphytestcase.presentation.screens.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.map
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import desperate.giphytestcase.data.mapper.mapDomainModelToView
import desperate.giphytestcase.databinding.FragmentFullScreenBinding
import desperate.giphytestcase.presentation.screens.fullscreen.adapter.GifFullScreenAdapter
import desperate.giphytestcase.utils.autoCleaned
import desperate.giphytestcase.utils.collectLifecycleFlow

@AndroidEntryPoint
class FullScreenFragment : Fragment() {

    private var binding: FragmentFullScreenBinding by autoCleaned()
    private val viewModel by viewModels<FullScreenViewModel>()
    private var adapter: GifFullScreenAdapter by autoCleaned()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = GifFullScreenAdapter()
        binding.pager.adapter = adapter
        observeViewModel()
    }

    private fun observeViewModel() {
        collectLifecycleFlow(viewModel.gifs) { gif->
            adapter.submitData(gif.map { mapDomainModelToView(it) })
        }
    }
}