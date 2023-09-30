package dev.byto.moly.ui.screen.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.byto.moly.R
import dev.byto.moly.databinding.FragmentDetailBinding
import dev.byto.moly.ui.adapter.CastAdapter
import dev.byto.moly.ui.adapter.ImageAdapter
import dev.byto.moly.ui.adapter.ReviewAdapter
import dev.byto.moly.ui.adapter.VideoAdapter
import dev.byto.moly.ui.screen.detail.dialog.VideoPlayerDialog
import dev.byto.moly.utils.ImageQuality
import dev.byto.moly.utils.LifecycleRecyclerView
import dev.byto.moly.utils.formatDate
import dev.byto.moly.utils.loadImage
import dev.byto.moly.utils.setAdapterWithFixedSize
import dev.byto.moly.utils.setGenreChips
import dev.byto.moly.utils.setSafeOnClickListener
import dev.byto.moly.utils.toHours
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding()
    private val viewModel: DetailViewModel by viewModels()

    private lateinit var adapterVideos: VideoAdapter
    private lateinit var adapterCast: CastAdapter
    private lateinit var adapterImages: ImageAdapter
    private lateinit var adapterReviews: ReviewAdapter

    private var snackbar: Snackbar? = null

    private var movieId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(
            binding.rvTrailer,
            binding.rvCast,
            binding.rvImage,
            binding.rvReviews
        )
        getArgs()
        initAdapters()
        viewModel.initRequests(movieId!!)
        collectFlows(
            listOf(
                ::collectMovieDetails,
                ::collectUiState
            )
        )
        binding.buttonBack.setSafeOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initAdapters() {
        binding.rvTrailer.let { rvTrailer->
            adapterVideos = VideoAdapter(context, ::actionPlay)
            rvTrailer.setAdapterWithFixedSize(adapterVideos, false)
        }
        binding.rvCast.let { rvCast->
            adapterCast = CastAdapter(context)
            rvCast.setAdapterWithFixedSize(adapterCast, false)
        }
        binding.rvImage.let { rvImage->
            adapterImages = ImageAdapter(context)
            rvImage.setAdapterWithFixedSize(adapterImages, false)
            rvImage.visibility = View.VISIBLE
        }
        binding.rvReviews.let { rvReviews->
            adapterReviews = ReviewAdapter(context)
            rvReviews.setAdapterWithFixedSize(adapterReviews, false)
            rvReviews.visibility = View.VISIBLE
        }
    }

    private fun getArgs() {
        val args: DetailFragmentArgs by navArgs()
        movieId = args.movieId
    }

    private fun actionPlay(url: String) {
        val videoDialog = VideoPlayerDialog(url)
        videoDialog.show(childFragmentManager, "Video Dialog")
    }

    private suspend fun collectMovieDetails() {
        viewModel.details.collect { details ->
            binding.cgGenres.setGenreChips(details.genres)

            val titleVideos = "Trailers (${details.videos.filterVideos().size})"
            binding.headingTrailer.text = titleVideos
            adapterVideos.submitList(details.videos.filterVideos())

            val titleCast = "Review (${details.credits.cast.size})"
            binding.headingCast.text = titleCast
            adapterCast.submitList(details.credits.cast)

            val titleReview = "Review (${details.review.results.size})"
            binding.headingReview.text = titleReview
            adapterReviews.submitList(details.review.results)

            val titleImage = "Images (${details.images.backdrops?.size})"
            binding.headingImage.text = titleImage
            adapterImages.submitList(details.images.backdrops)

            binding.detailsBannerImage.loadImage(
                details.backdropPath, ImageQuality.HIGH,
                circleCrop = false, fitTop = false, isThumbnail = false,
                errorImage = ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_broken)
            )
            binding.imagePoster.loadImage(
                details.posterPath, ImageQuality.MEDIUM,
                circleCrop = false, fitTop = false, isThumbnail = false,
                errorImage = ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_broken)
            )
            binding.textMovieName.text = details.originalTitle
            binding.textOverview.text = details.overview
            binding.textReleaseDate.text = details.releaseDate.formatDate()
            val rate = String.format("%.1f", details.voteAverage)
            binding.textRating.text = rate
            binding.ratingBar.rating = rate.toFloat()/2
            binding.textLength.text = details.runtime!!.toHours()
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!, actionText = getString(R.string.retry)
            ) {
                viewModel.retryConnection {
                    viewModel.initRequests(movieId!!)
                }
            }
        }
    }

    private fun manageRecyclerViewAdapterLifecycle(vararg recyclerViews: RecyclerView) {
        recyclerViews.forEach { recyclerView ->
            viewLifecycleOwner.lifecycle.addObserver(
                LifecycleRecyclerView(recyclerView)
            )
        }
    }

    private fun collectFlows(collectors: List<KSuspendFunction0<Unit>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectors.forEach { collector ->
                    launch {
                        collector()
                    }
                }
            }
        }
    }

    private fun showSnackbar(
        message: String,
        indefinite: Boolean = true,
        actionText: String? = null,
        anchor: Boolean = false,
        action: (() -> Unit)? = null
    ) {
        val view = activity?.window?.decorView?.rootView
        val length = if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        val snackbar = view?.let { Snackbar.make(it, message, length) }

        if (action != null) snackbar?.setAction(actionText) { action() }
        if (anchor) snackbar?.setAnchorView(R.id.nav_view)

        this.snackbar = snackbar
        this.snackbar?.show()
    }

    override fun onPause() {
        super.onPause()
        this.snackbar?.dismiss()
    }
}