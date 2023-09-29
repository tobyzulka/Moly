package dev.byto.moly.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CountryDTO(
    @SerializedName("name")
    val name: String
)