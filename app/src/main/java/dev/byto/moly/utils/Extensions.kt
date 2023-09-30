package dev.byto.moly.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import dev.byto.moly.R
import dev.byto.moly.domain.model.Genre
import jp.wasabeef.glide.transformations.CropTransformation
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.floor
import kotlin.math.roundToInt

fun View.doOnApplyWindowInsets(block: (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat) {

    val initialPadding = recordInitialPaddingForView(this)

    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }

    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

fun View.updateMargin(
    left: Int = marginLeft,
    top: Int = marginTop,
    right: Int = marginRight,
    bottom: Int = marginBottom
) = updateLayoutParams<ViewGroup.MarginLayoutParams> { updateMargins(left, top, right, bottom) }

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = DebounceSafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun RecyclerView.setAdapterWithFixedSize(
    adapter: RecyclerView.Adapter<*>,
    hasFixedSize: Boolean = false
) {
    this.adapter = adapter
    this.setHasFixedSize(hasFixedSize)
}

@SuppressLint("CheckResult")
fun ImageView.loadImage(
    posterPath: String?,
    quality: ImageQuality?,
    circleCrop: Boolean?,
    fitTop: Boolean,
    isThumbnail: Boolean,
    errorImage: Drawable?
) {
    val imageUrl = if (isThumbnail) "https://img.youtube.com/vi/$posterPath/0.jpg" else quality?.imageBaseUrl + posterPath

    val glide = Glide.with(context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(errorImage ?: AppCompatResources.getDrawable(context, R.drawable.ic_image_broken))
        .dontAnimate()

    if (circleCrop == true) glide.circleCrop()
    if (fitTop) glide.apply(RequestOptions.bitmapTransform(CropTransformation(0, 1235, CropTransformation.CropType.TOP)))

    glide.into(this)
}

fun String?.formatDate(): String {
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return if (!this.isNullOrEmpty()) outputFormat.format(inputFormat.parse(this)!!) else ""
}

fun ChipGroup.setGenreChips(
    genres: List<Genre>
) {
    genres.forEach { genre ->
        addView(
            Chip(context).apply {
                text = genre.name
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                textSize = 24.sp().toFloat()
                width = 40.dp()
                height = 20.dp()
                textEndPadding = 2.dp().toFloat()
                textStartPadding = 2.dp().toFloat()
            }
        )
    }
}

fun Int.isDarkColor(): Boolean {
    val darkness = 1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness >= 0.5
}

fun Int.setTintColor(reverse: Boolean = false): Int = if (this.isDarkColor() xor reverse) Color.WHITE else Color.BLACK

fun Int.toHours() : String {
    var min = this
    var time : String = ""
    if (min > 0) {
        time = "${min/60} hrs"
        min %= 60
    }
    if (min > 0) {
        time = "$time $min mins"
    }
    return time
}

fun Int.px() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

fun Int.dp() = (this / Resources.getSystem().displayMetrics.density).roundToInt()

fun Int.sp() = (this / Resources.getSystem().displayMetrics.scaledDensity).roundToInt()