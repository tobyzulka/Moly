package dev.byto.moly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.byto.moly.R
import dev.byto.moly.databinding.ItemImageBinding
import dev.byto.moly.domain.model.Image
import dev.byto.moly.ui.adapter.ImageAdapter.ImageViewHolder
import dev.byto.moly.utils.ImageQuality
import dev.byto.moly.utils.getImageDiffUtils
import dev.byto.moly.utils.loadImage

class ImageAdapter(
    private val mContext: Context?
) : ListAdapter<Image, ImageViewHolder>(getImageDiffUtils()) {

    class ImageViewHolder(val view: ItemImageBinding) :
        RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position).let {
            holder.view.apply {
                imageLandscape.loadImage(
                    it.filePath,ImageQuality.MEDIUM,
                    circleCrop = false, fitTop = false, isThumbnail = false,
                    errorImage = ContextCompat.getDrawable(mContext!!, R.drawable.ic_image_broken)
                )
            }
        }
    }
}