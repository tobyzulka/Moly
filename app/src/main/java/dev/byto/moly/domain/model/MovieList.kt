package dev.byto.moly.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MovieList(
    val results: List<Movie>,
    val totalResults: Int,
    val totalPages: Int,
) {
    companion object {
        val empty = MovieList(
            results = emptyList(),
            totalResults = 0,
            totalPages = 0
        )
    }
}

@Parcelize
data class Movie(
    val id: Int,
    val backdropPath: String?,
    val overview: String?,
    val popularity: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val voteAverage: Double
) : Parcelable