package dev.byto.moly.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MovieDetailDTO(
    @SerializedName("budget")
    val budget: Long,
    @SerializedName("credits")
    val credits: CreditsDTO,
    @SerializedName("genres")
    val genres: List<GenreDTO>,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: ImageListDTO,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<CompanyDTO>,
    @SerializedName("production_countries")
    val productionCountries: List<CountryDTO>,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<LanguageDTO>,
    @SerializedName("reviews")
    val review: ReviewListDTO,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("videos")
    val videos: VideoListDTO,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)