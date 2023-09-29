package dev.byto.moly.data.remote.api

import dev.byto.moly.data.remote.dto.GenreListDTO
import dev.byto.moly.data.remote.dto.MovieDetailDTO
import dev.byto.moly.data.remote.dto.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreListDTO

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): MovieListDTO

    @GET("movie/{movie_id}?&append_to_response=credits,videos,images,reviews")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): MovieDetailDTO
}