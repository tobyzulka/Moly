package dev.byto.moly.ui.screen.detail

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import dagger.hilt.android.AndroidEntryPoint
import dev.byto.moly.R
import dev.byto.moly.databinding.FragmentDetailBinding
import dev.byto.moly.ui.adapter.ImageAdapter
import dev.byto.moly.ui.adapter.CastAdapter
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
        }
        binding.rvReviews.let { rvReviews->
            adapterReviews = ReviewAdapter(context)
            rvReviews.setAdapterWithFixedSize(adapterReviews, false)
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
            binding.cgGenres.setGenreChips(details.genres, isDarkModeOn())
            adapterVideos.submitList(details.videos.filterVideos())
            adapterCast.submitList(details.credits.cast)
            adapterImages.submitList(details.images.backdrops)
            adapterReviews.submitList(details.review.results)
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
            val rate = "%.1f".format(details.voteAverage)
            binding.textRating.text = rate
            binding.ratingBar.rating = details.voteAverage.toFloat()
            binding.textLength.text = details.runtime!!.toHours()
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) {
                Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()
                viewModel.retryConnection {
//                    viewModel.initRequests(movieId!!)
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

    private fun isDarkModeOn(): Boolean {
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }
}