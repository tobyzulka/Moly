package dev.byto.moly.ui.screen.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.byto.moly.R
import dev.byto.moly.databinding.FragmentHomeBinding
import dev.byto.moly.domain.model.Genre
import dev.byto.moly.ui.adapter.GenreAdapter
import dev.byto.moly.utils.Constants.URL_LINKEDIN
import dev.byto.moly.utils.setAdapterWithFixedSize
import dev.byto.moly.utils.setSafeOnClickListener
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    private var adapterGenres: GenreAdapter = GenreAdapter(::clickAction)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        viewModel.initRequests()
        collectFlows(
            listOf(
                ::collectGenreResults,
                ::collectUiState
            )
        )
        actionToProfile()
    }
    private fun actionToProfile() {
        binding.homeAboutButton.setSafeOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_LINKEDIN))
            startActivity(browserIntent)
        }

    }

    private fun initAdapters() {
        binding.rvGenres.let { rvGenre ->
            rvGenre.layoutManager =
                GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            rvGenre.setAdapterWithFixedSize(adapterGenres, false)
        }
    }

    private fun clickAction(genre: Genre) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToNavigationViewAll(genreId = genre.id, genreName = genre.name)
        )
    }

    private suspend fun collectGenreResults() {
        viewModel.genreResults.collect { genres ->
            adapterGenres.submitList(genres)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) {
                Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()
                viewModel.retryConnection {
                    viewModel.initRequests()
                }
            }
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