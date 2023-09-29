package dev.byto.moly.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GenreList(
    val genres: List<Genre>?
) {
    companion object {
        val empty = GenreList(
            genres = null
        )
    }
}

@Parcelize
data class Genre(
    val id: Int,
    val name: String
) : Parcelable