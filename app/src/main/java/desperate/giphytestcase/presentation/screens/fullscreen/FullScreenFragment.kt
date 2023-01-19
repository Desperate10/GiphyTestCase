package desperate.giphytestcase.presentation.screens.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import desperate.giphytestcase.databinding.FragmentFullScreenBinding
import desperate.giphytestcase.presentation.screens.fullscreen.adapter.GifFullScreenAdapter
import desperate.giphytestcase.utils.autoCleaned
import desperate.giphytestcase.utils.collectLifecycleFlow

@AndroidEntryPoint
class FullScreenFragment : Fragment() {

    private var binding: FragmentFullScreenBinding by autoCleaned()
    private val viewModel by viewModels<FullScreenViewModel>()
    private var adapter: GifFullScreenAdapter by autoCleaned()
    private val args by navArgs<FullScreenFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        if(savedInstanceState == null) {
            restorePagerPosition()
        }
        observeViewModel()
    }

    private fun setupAdapter() {
        adapter = GifFullScreenAdapter()
        binding.pager.adapter = adapter
    }

    private fun observeViewModel() {
        collectLifecycleFlow(viewModel.gifs) { gif->
            adapter.submitData(gif)
        }
    }

    private fun restorePagerPosition() {
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.pager.setCurrentItem(args.offset, false)
                binding.pager.unregisterOnPageChangeCallback(this)
            }
        })
    }
}