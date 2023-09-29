package dev.byto.moly.domain.repository

import dev.byto.moly.domain.model.GenreList
import dev.byto.moly.domain.model.MovieDetail
import dev.byto.moly.domain.model.MovieList
import dev.byto.moly.utils.ResultWrapper

interface MovieRepository {
    suspend fun getGenres(): ResultWrapper<GenreList>
    suspend fun getMoviesByGenre(genreId: Int, page: Int): ResultWrapper<MovieList>
    suspend fun getMovieDetails(movieId: Int): ResultWrapper<MovieDetail>
}