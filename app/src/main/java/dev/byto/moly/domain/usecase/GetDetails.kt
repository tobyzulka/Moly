package dev.byto.moly.domain.usecase

import dev.byto.moly.domain.repository.MovieRepository
import dev.byto.moly.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetails @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        movieId: Int
    ): Flow<ResultWrapper<Any>> = flow {
        emit(
            movieRepository.getMovieDetails(movieId)
        )
    }
}