package dev.byto.moly.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewListDTO(
	@SerializedName("results")
	val results: List<ReviewDTO?>,
	@SerializedName("total_results")
	val totalResults: Int
)

data class ReviewDTO(
	@SerializedName("author_details")
	val authorDetails: AuthorDTO,
	@SerializedName("author")
	val author: String?,
	@SerializedName("created_at")
	val createdAt: String?,
	@SerializedName("updated_at")
	val updatedAt: String?,
	@SerializedName("id")
	val id: String?,
	@SerializedName("content")
	val content: String?,
	@SerializedName("url")
	val url: String?,
)

data class AuthorDTO(
	@SerializedName("avatar_path")
	val avatarPath: String?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("rating")
	val rating: Double?,
	@SerializedName("username")
	val username: String?
)
