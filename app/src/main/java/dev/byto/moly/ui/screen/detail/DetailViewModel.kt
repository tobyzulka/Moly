package dev.byto.moly.ui.screen.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.byto.moly.domain.model.MovieDetail
import dev.byto.moly.domain.usecase.GetDetails
import dev.byto.moly.ui.UiState
import dev.byto.moly.ui.base.BaseViewModel
import dev.byto.moly.utils.Constants
import dev.byto.moly.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetails: GetDetails
) : BaseViewModel() {

    private val _details = MutableStateFlow(MovieDetail.empty)
    val details get() = _details.asStateFlow()
    private val appendTo: String = ""

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            getDetails(
                movieId = detailId,
//                appendTo = appendTo
            ).collect { response ->
                when (response) {
                    is ResultWrapper.Success -> {
                        val result = (response.value as MovieDetail)
                        _details.value = result
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

    fun initRequests(movieId: Int) {
        detailId = movieId
        fetchMovieDetails()
    }
}