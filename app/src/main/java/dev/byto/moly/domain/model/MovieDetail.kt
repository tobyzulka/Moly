package dev.byto.moly.domain.model

data class MovieDetail(
    val budget: Long,
    val credits: Credits,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val images: ImageList,
    val originalTitle: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val productionCompanies: List<Company>,
    val productionCountries: List<Country>,
    val releaseDate: String?,
    val revenue: Long,
    val runtime: Int?,
    val spokenLanguages: List<Language>,
    val review: ReviewList,
    val status: String,
    val title: String,
    val videos: VideoList,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun trimGenreList(): String = genres.joinToString { it.name }
    fun trimProductionCompanyList(): String = productionCompanies.joinToString {
        it.name + if (it.originCountry.isNotEmpty()) " (${it.originCountry})" else ""
    }

    fun trimProductionCountryList(): String = productionCountries.joinToString { it.name }
    fun trimSpokenLanguageList(): String = spokenLanguages.joinToString { it.englishName }

    companion object {
        val empty = MovieDetail(
            budget = 0L,
            credits = Credits.empty,
            genres = emptyList(),
            homepage = null,
            id = 0,
            images = ImageList.empty,
            originalTitle = "",
            overview = null,
            posterPath = null,
            backdropPath = null,
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            releaseDate = null,
            revenue = 0L,
            runtime = 0,
            spokenLanguages = emptyList(),
            review = ReviewList.empty,
            status = "",
            title = "",
            videos = VideoList.empty,
            voteAverage = 0.0,
            voteCount = 0
        )
    }
}