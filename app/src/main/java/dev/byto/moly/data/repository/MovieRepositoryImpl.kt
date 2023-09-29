package dev.byto.moly.data.repository

import dev.byto.moly.data.mapper.toGenreList
import dev.byto.moly.data.mapper.toMovieDetail
import dev.byto.moly.data.mapper.toMovieList
import dev.byto.moly.data.remote.api.MovieApi
import dev.byto.moly.domain.model.GenreList
import dev.byto.moly.domain.model.MovieDetail
import dev.byto.moly.domain.model.MovieList
import dev.byto.moly.domain.repository.MovieRepository
import dev.byto.moly.utils.SafeApiCall
import dev.byto.moly.utils.ResultWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val safeApiCall: SafeApiCall
) : MovieRepository {
    override suspend fun getGenres(): ResultWrapper<GenreList> = safeApiCall.execute {
        api.getGenres().toGenreList()
    }

    override suspend fun getMoviesByGenre(
        genreId: Int,
        page: Int
    ): ResultWrapper<MovieList> = safeApiCall.execute {
        api.getMoviesByGenre(genreId, page).toMovieList()
    }

    override suspend fun getMovieDetails(
        movieId: Int,
    ): ResultWrapper<MovieDetail> = safeApiCall.execute {
        api.getMovieDetails(movieId).toMovieDetail()
    }

}