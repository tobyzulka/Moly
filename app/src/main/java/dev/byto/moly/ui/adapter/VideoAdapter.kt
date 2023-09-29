package dev.byto.moly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.byto.moly.R
import dev.byto.moly.databinding.ItemVideoBinding
import dev.byto.moly.domain.model.Video
import dev.byto.moly.ui.adapter.VideoAdapter.VideoViewHolder
import dev.byto.moly.utils.ImageQuality
import dev.byto.moly.utils.formatDate
import dev.byto.moly.utils.getVideoDiffUtils
import dev.byto.moly.utils.loadImage
import dev.byto.moly.utils.setSafeOnClickListener

class VideoAdapter(
    private val mContext: Context?,
    private val clickAction: (String) -> Unit

) : ListAdapter<Video, VideoViewHolder>(getVideoDiffUtils()) {

    inner class VideoViewHolder(
        val view: ItemVideoBinding
    ) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        getItem(position).let { video ->
            holder.view.apply {
                textVideoName.text = video.name
                imagePreview.loadImage(
                    video.key,
                    ImageQuality.MEDIUM,
                    false, fitTop = false, isThumbnail = true,
                    errorImage = ContextCompat.getDrawable(mContext!!, R.drawable.ic_movie_creation)
                )
                textReleaseDate.text = video.publishedAt.formatDate()
                holder.view.imagePreview.setSafeOnClickListener {
                    clickAction(video.key)
                }
            }
        }
    }
}