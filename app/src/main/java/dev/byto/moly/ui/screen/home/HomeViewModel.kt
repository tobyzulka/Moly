package dev.byto.moly.ui.screen.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.byto.moly.domain.model.Genre
import dev.byto.moly.domain.model.GenreList
import dev.byto.moly.domain.usecase.GetGenres
import dev.byto.moly.ui.UiState
import dev.byto.moly.ui.base.BaseViewModel
import dev.byto.moly.utils.Constants
import dev.byto.moly.utils.ResultWrapper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenres: GetGenres
) : BaseViewModel() {

    private val _genreResults = MutableStateFlow(emptyList<Genre>())
    val genreResults get() = _genreResults.asStateFlow()

    private suspend fun fetchGenreResults() {
        getGenres().collect { response ->
            when (response) {
                is ResultWrapper.Success -> {
                    val result = (response.value as GenreList).genres
                    _genreResults.value = result!!
                    _uiState.value = UiState.successState()
                }

                is ResultWrapper.GenericError -> {
                    errorText = response.messageResponse.toString()
                }

                is ResultWrapper.NetworkError -> {
                    errorText = Constants.NETWORK_CONNECTION_PROBLEM
                }
            }
        }
    }

    fun initRequests() {
        viewModelScope.launch {
            coroutineScope {
                launch {
                    fetchGenreResults()
                }
            }
            setUiState()
        }
    }

}