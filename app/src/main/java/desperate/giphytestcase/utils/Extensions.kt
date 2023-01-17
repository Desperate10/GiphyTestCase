package desperate.giphytestcase.utils

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Fragment.collectLifecycleFlow(flow: (Flow<T?>)?, block: suspend (t: T) -> Unit) {
    flow ?: return
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                it?.also {
                    block(it)
                }
            }
        }
    }
}

interface SearchViewQueryTextCallback {
    fun onQueryTextSubmit(query: String?)
}

fun SearchView.setupQueryTextSubmit (callback: SearchViewQueryTextCallback) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            callback.onQueryTextSubmit(query)
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            return false
        }
    })
}

fun <T : Any> Fragment.autoCleaned(initializer: (() -> T)? = null): AutoCleanedValue<T> {
    return AutoCleanedValue(this, initializer)
}