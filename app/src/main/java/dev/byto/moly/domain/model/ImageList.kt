package dev.byto.moly.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ImageList(
    val backdrops: List<Image>?,
    val posters: List<Image>?
) {
    companion object {
        val empty = ImageList(
            backdrops = emptyList(),
            posters = emptyList()
        )
    }
}

@Parcelize
data class Image(
    val filePath: String
) : Parcelable