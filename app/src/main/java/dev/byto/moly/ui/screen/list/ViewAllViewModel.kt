package dev.byto.moly.ui.screen.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.byto.moly.domain.model.MovieList
import dev.byto.moly.domain.usecase.GetByGenre
import dev.byto.moly.ui.UiState
import dev.byto.moly.ui.base.BaseViewModel
import dev.byto.moly.utils.Constants
import dev.byto.moly.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllViewModel @Inject constructor(
    private val getByGenre: GetByGenre
) : BaseViewModel() {

    private val _results = MutableStateFlow(setOf<Any>())
    val results get() = _results.asStateFlow()

    private var page = 1
    private var genreId: Int? = null

    private fun fetchMovieResults() {
        viewModelScope.launch {
            getByGenre(
                genreId = genreId!!,
                page = page
            ).collect { response ->
                when (response) {
                    is ResultWrapper.Success -> {
                        val result = (response.value as MovieList).results
                        _results.value += result
                        _uiState.value = UiState.successState()
                    }

                    is ResultWrapper.GenericError -> {
                        _uiState.value = UiState.errorState(errorText = response.messageResponse.toString())
                    }

                    is ResultWrapper.NetworkError -> {
                        _uiState.value = UiState.errorState(errorText = Constants.NETWORK_CONNECTION_PROBLEM)
                    }
                }
            }
        }
    }

    fun onLoadMore() {
        _uiState.value = UiState.loadingState()
        page++

        fetchMovieResults()
    }

    fun initRequests(genreId: Int?) {
        this.genreId = genreId
        fetchMovieResults()
        _uiState.value = UiState.successState()
    }
}