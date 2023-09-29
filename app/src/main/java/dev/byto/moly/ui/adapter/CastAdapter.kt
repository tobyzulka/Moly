package dev.byto.moly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.byto.moly.R
import dev.byto.moly.databinding.ItemPersonBinding
import dev.byto.moly.domain.model.Person
import dev.byto.moly.ui.adapter.CastAdapter.CastViewHolder
import dev.byto.moly.utils.ImageQuality
import dev.byto.moly.utils.getPersonDiffUtils
import dev.byto.moly.utils.loadImage

class CastAdapter(
    private val mContext: Context?
) : ListAdapter<Person, CastViewHolder>(getPersonDiffUtils()) {

    inner class CastViewHolder(val view: ItemPersonBinding) :
        RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        getItem(position).let { cast ->
            holder.view.apply {
                textName.text = cast.name
                val job = "${cast.character} (${cast.job})"
                textJob.text = job
                imagePerson.loadImage(
                    cast.profilePath, ImageQuality.LOW,
                    circleCrop = true, fitTop = false, isThumbnail = false,
                    errorImage = ContextCompat.getDrawable(mContext!!, R.drawable.ic_person)
                )
            }
        }
    }
}