package dev.byto.moly.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.byto.moly.databinding.ItemGenreBinding
import dev.byto.moly.domain.model.Genre
import dev.byto.moly.utils.getGenreDiffUtils
import dev.byto.moly.utils.setSafeOnClickListener

class GenreAdapter(
    private val clickAction: (Genre) -> Unit
) : ListAdapter<Genre, GenreAdapter.GenreViewHolder> (getGenreDiffUtils()) {

    class GenreViewHolder(
        val view: ItemGenreBinding
    ) : RecyclerView.ViewHolder(view.root)

    override fun onBindViewHolder(
        holder: GenreViewHolder,
        position: Int
    ) {
        getItem(position).let { genre ->
            holder.view.apply {
                textTitle.text = genre.name
                holder.view.cvGenre.setSafeOnClickListener {
                    clickAction(genre)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        return GenreViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }
}