package dev.byto.moly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.byto.moly.R
import dev.byto.moly.databinding.ItemReviewBinding
import dev.byto.moly.domain.model.Review
import dev.byto.moly.ui.adapter.ReviewAdapter.*
import dev.byto.moly.utils.ImageQuality
import dev.byto.moly.utils.formatDate
import dev.byto.moly.utils.getReviewDiffUtils
import dev.byto.moly.utils.loadImage

class ReviewAdapter(
    private val mContext: Context?
) : ListAdapter<Review, ReviewViewHolder>(getReviewDiffUtils()) {

    inner class ReviewViewHolder(
        val view: ItemReviewBinding
    ) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        getItem(position).let { review ->
            holder.view.apply {
                textName.text = review.author
                imageAvatar.loadImage(
                    review.authorDetails.avatarPath,
                    ImageQuality.LOW,
                    circleCrop = true, fitTop = false, isThumbnail = false,
                    errorImage = ContextCompat.getDrawable(mContext!!, R.drawable.ic_movie_creation)
                )
                textCreatedDate.text = review.createdAt.formatDate()
                val rate = "%.1f".format(review.authorDetails.rating)
                textRating.text = rate
                textComment.text = review.content
            }
        }
    }
}