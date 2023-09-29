package dev.byto.moly.utils

import androidx.recyclerview.widget.DiffUtil
import dev.byto.moly.domain.model.Genre
import dev.byto.moly.domain.model.Image
import dev.byto.moly.domain.model.Movie
import dev.byto.moly.domain.model.Person
import dev.byto.moly.domain.model.Review
import dev.byto.moly.domain.model.Video

fun getGenreDiffUtils() = object : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(
        oldItem: Genre,
        newItem: Genre
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Genre,
        newItem: Genre
    ): Boolean = oldItem == newItem
}

fun getMovieDiffUtils() = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean = oldItem == newItem
}

fun getImageDiffUtils() = object : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(
        oldItem: Image,
        newItem: Image
    ): Boolean = oldItem.filePath == newItem.filePath

    override fun areContentsTheSame(
        oldItem: Image,
        newItem: Image
    ): Boolean = oldItem == newItem
}

fun getPersonDiffUtils() = object : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean = oldItem == newItem
}

fun getVideoDiffUtils() = object : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(
        oldItem: Video,
        newItem: Video
    ): Boolean = oldItem.key == newItem.key

    override fun areContentsTheSame(
        oldItem: Video,
        newItem: Video
    ): Boolean = oldItem == newItem
}

fun getReviewDiffUtils() = object : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(
        oldItem: Review,
        newItem: Review
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Review,
        newItem: Review
    ): Boolean = oldItem == newItem
}