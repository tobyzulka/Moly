package dev.byto.moly.utils

enum class ImageQuality(val imageBaseUrl: String) {
    LOW("https://image.tmdb.org/t/p/w300"),
    MEDIUM("https://image.tmdb.org/t/p/w500"),
    HIGH("https://image.tmdb.org/t/p/w780"),
    ORIGINAL("https://image.tmdb.org/t/p/original")
}