package dev.byto.moly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.byto.moly.R
import dev.byto.moly.databinding.ItemMovieBinding
import dev.byto.moly.domain.model.Movie
import dev.byto.moly.utils.ImageQuality
import dev.byto.moly.utils.getMovieDiffUtils
import dev.byto.moly.utils.loadImage
import dev.byto.moly.utils.setSafeOnClickListener

class MovieAdapter(
    private val clickAction: ((Int) -> Unit)? = null,
    private val mContext: Context?
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(getMovieDiffUtils()) {

    class MovieViewHolder(
        val view: ItemMovieBinding
    ) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        getItem(position).let { movie ->
            holder.view.apply {
                textTitle.text = movie.title
                imagePoster.loadImage(
                    movie.posterPath,
                    ImageQuality.LOW,
                    false, fitTop = false, isThumbnail = false,
                    errorImage = ContextCompat.getDrawable(mContext!!, R.drawable.ic_image_broken)
                )
                textRating.text = String.format("%.1f", movie.voteAverage)
                holder.view.layoutMovie.setSafeOnClickListener {
                    clickAction?.invoke(movie.id)
                }
            }
        }
    }
}