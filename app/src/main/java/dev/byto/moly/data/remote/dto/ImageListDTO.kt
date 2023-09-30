package dev.byto.moly.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ImageListDTO(
    @SerializedName("backdrops")
    val backdrops: List<ImageDTO>?,
    @SerializedName("posters")
    val posters: List<ImageDTO>?,
)

data class ImageDTO(
    @SerializedName("file_path")
    val filePath: String
)