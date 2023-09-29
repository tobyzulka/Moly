package dev.byto.moly.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GenreListDTO(
    @SerializedName("genres")
    val genres: List<GenreDTO>
)

data class GenreDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)