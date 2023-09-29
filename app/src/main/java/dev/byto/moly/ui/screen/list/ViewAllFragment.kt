package dev.byto.moly.ui.screen.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.byto.moly.R
import dev.byto.moly.databinding.FragmentViewAllBinding
import dev.byto.moly.domain.model.Movie
import dev.byto.moly.ui.adapter.MovieAdapter
import dev.byto.moly.utils.LifecycleRecyclerView
import dev.byto.moly.utils.setAdapterWithFixedSize
import dev.byto.moly.utils.setSafeOnClickListener
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

@AndroidEntryPoint
class ViewAllFragment : Fragment(R.layout.fragment_view_all) {

    private val binding: FragmentViewAllBinding by viewBinding()
    private val viewModel: ViewAllViewModel by viewModels()

    private lateinit var adapterMovies: MovieAdapter

    private var genreId: Int? = null
    private var genreName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        binding.textTitle.text = genreName
        viewModel.initRequests(genreId)
        initAdapters()
        collectFlows(
            listOf(
                ::collectMovieResults,
                ::collectUiState
            )
        )
        binding.btnBack.setSafeOnClickListener {
            findNavController().navigateUp()
        }

        manageRecyclerViewAdapterLifecycle(binding.rvMovies)
    }


    private fun getArgs() {
        val args: ViewAllFragmentArgs by navArgs()
        genreId = args.genreId
        genreName = args.genreName
    }

    private fun initAdapters() {
        binding.rvMovies.let { rvMovies ->
            adapterMovies = MovieAdapter(::clickAction, context)
            rvMovies.layoutManager =
                GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            rvMovies.setAdapterWithFixedSize(adapterMovies, true)
            rvMovies.addInfiniteScrollListener()
//            loadMoreOnRecyclerView(rvMovies)
        }
    }

    private fun loadMoreOnRecyclerView(mRecyclerView: RecyclerView) {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    viewModel.onLoadMore()
                }
            }
        })
    }

    private fun RecyclerView.addInfiniteScrollListener() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val layoutManagerType = layoutManager as GridLayoutManager
            private val visibleThreshold = 10
            private var loading = true
            private var previousTotal = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val visibleItemCount = layoutManagerType.childCount
                val totalItemCount = layoutManagerType.itemCount
                val firstVisibleItem = layoutManagerType.findFirstVisibleItemPosition()

                if (totalItemCount < previousTotal) previousTotal = 0

                if (loading && totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }

                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    viewModel.onLoadMore()
                    loading = true
                }
            }
        })
    }

    private fun clickAction(movieId: Int) {
        findNavController().navigate(
            ViewAllFragmentDirections.actionNavigationViewAllToNavigationDetail(movieId)
        )
    }

    private suspend fun collectMovieResults() {
        viewModel.results.collect { movies ->
            val movieList = movies as Set<Movie>
            adapterMovies.submitList(movieList.toList())
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) {
                Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()
                viewModel.retryConnection {
                    viewModel.initRequests(genreId)
                }
            }
            binding.loading.isVisible = state.isLoading
        }
    }

    private fun manageRecyclerViewAdapterLifecycle(vararg recyclerViews: RecyclerView) {
        recyclerViews.forEach { recyclerView ->
            viewLifecycleOwner.lifecycle.addObserver(
                LifecycleRecyclerView(recyclerView)
            )
        }
    }

    private fun collectFlows(collectors: List<KSuspendFunction0<Unit>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectors.forEach { collector ->
                    launch {
                        collector()
                    }
                }
            }
        }
    }
}