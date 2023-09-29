package dev.byto.moly.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ReviewList(
    val results: List<Review>,
    val totalResults: Int
) {
    companion object {
        val empty = ReviewList(
            results = emptyList(),
            totalResults = 0
        )
    }
}

@Parcelize
data class Review(
    val author: String?,
    val authorDetails: Author,
    val content: String?,
    val id: String?,
    val url: String?,
    val createdAt: String?,
    val updatedAt: String?
) : Parcelable

@Parcelize
data class Author(
    val name: String?,
    val username: String?,
    val avatarPath: String?,
    val rating: Int?
) : Parcelable