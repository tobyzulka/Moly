package dev.byto.moly.data.mapper

import dev.byto.moly.data.remote.dto.AuthorDTO
import dev.byto.moly.data.remote.dto.CompanyDTO
import dev.byto.moly.data.remote.dto.CountryDTO
import dev.byto.moly.data.remote.dto.CreditsDTO
import dev.byto.moly.data.remote.dto.GenreDTO
import dev.byto.moly.data.remote.dto.GenreListDTO
import dev.byto.moly.data.remote.dto.ImageDTO
import dev.byto.moly.data.remote.dto.ImageListDTO
import dev.byto.moly.data.remote.dto.LanguageDTO
import dev.byto.moly.data.remote.dto.MovieDTO
import dev.byto.moly.data.remote.dto.MovieDetailDTO
import dev.byto.moly.data.remote.dto.MovieListDTO
import dev.byto.moly.data.remote.dto.PersonDTO
import dev.byto.moly.data.remote.dto.PersonListDTO
import dev.byto.moly.data.remote.dto.ReviewDTO
import dev.byto.moly.data.remote.dto.ReviewListDTO
import dev.byto.moly.data.remote.dto.VideoDTO
import dev.byto.moly.data.remote.dto.VideoListDTO
import dev.byto.moly.domain.model.Author
import dev.byto.moly.domain.model.Company
import dev.byto.moly.domain.model.Country
import dev.byto.moly.domain.model.Credits
import dev.byto.moly.domain.model.Genre
import dev.byto.moly.domain.model.GenreList
import dev.byto.moly.domain.model.Image
import dev.byto.moly.domain.model.ImageList
import dev.byto.moly.domain.model.Language
import dev.byto.moly.domain.model.Movie
import dev.byto.moly.domain.model.MovieDetail
import dev.byto.moly.domain.model.MovieList
import dev.byto.moly.domain.model.Person
import dev.byto.moly.domain.model.PersonList
import dev.byto.moly.domain.model.Review
import dev.byto.moly.domain.model.ReviewList
import dev.byto.moly.domain.model.Video
import dev.byto.moly.domain.model.VideoList

fun MovieListDTO.toMovieList(): MovieList = MovieList(
    results = results.map { it.toMovie() },
    totalResults = totalResults,
    totalPages = totalPages
)

fun MovieDTO.toMovie(): Movie = Movie(
    id = id,
    backdropPath = backdropPath,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage
)

fun MovieDetailDTO.toMovieDetail(): MovieDetail = MovieDetail(
    budget = budget,
    credits = credits.toCredits(),
    genres = genres.map { it.toGenre() },
    homepage = homepage,
    id = id,
    images = images.toImageList(),
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    productionCompanies = productionCompanies.map { it.toCompany() },
    productionCountries = productionCountries.map { it.toCountry() },
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    spokenLanguages = spokenLanguages.map { it.toLanguage() },
    review = review.toReviewList(),
    status = status,
    title = title,
    videos = videos.toVideoList(),
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun GenreListDTO.toGenreList(): GenreList = GenreList(
    genres = genres.map { it.toGenre() }
)

fun GenreDTO.toGenre(): Genre = Genre(
    id = id,
    name = name
)

fun ImageListDTO.toImageList(): ImageList = ImageList(
    backdrops = backdrops?.map { it.toImage() },
    posters = posters?.map { it.toImage() },
    profiles = profiles?.map { it.toImage() },
    stills = stills?.map { it.toImage() }
)

fun ImageDTO.toImage(): Image = Image(
    filePath = filePath
)

fun LanguageDTO.toLanguage(): Language = Language(
    englishName = englishName
)

fun CompanyDTO.toCompany(): Company = Company(
    name = name,
    originCountry = originCountry
)

fun CountryDTO.toCountry(): Country = Country(
    name = name
)

fun CreditsDTO.toCredits(): Credits = Credits(
    cast = cast.map { it.toPerson() },
    crew = crew.map { it.toPerson() },
    guestStars = guestStars?.map { it.toPerson() }
)

fun PersonListDTO.toPersonList(): PersonList = PersonList(
    results = results.map { it.toPerson() },
    totalResults = totalResults
)

fun PersonDTO.toPerson(): Person = Person(
    character = character,
    department = department,
    id = id,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    profilePath = profilePath
)

fun VideoListDTO.toVideoList(): VideoList = VideoList(
    results = results.map { it.toVideo() }
)

fun VideoDTO.toVideo(): Video = Video(
    key = key,
    name = name,
    publishedAt = publishedAt,
    site = site,
    type = type
)

fun ReviewListDTO.toReviewList(): ReviewList = ReviewList(
    results = results.map { it!!.toReview() },
    totalResults = totalResults
)

fun ReviewDTO.toReview(): Review = Review(
    author = author,
    authorDetails = authorDetails.toAuthor(),
    content = content,
    id = id,
    url = url,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun AuthorDTO.toAuthor(): Author = Author(
    name = name,
    username = username,
    avatarPath = avatarPath,
    rating = rating
)


