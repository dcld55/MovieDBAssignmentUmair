package id.indocyber.themoviedbumair.fragment.detail

import android.view.View
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.google.android.material.chip.Chip
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.ui.BaseFragment
import id.indocyber.themoviedbumair.R
import id.indocyber.themoviedbumair.databinding.LayoutDetailFragmentBinding
import id.indocyber.themoviedbumair.view_model.DetailVideoReviewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailVideoReviewViewModel, LayoutDetailFragmentBinding>() {
    override val vm: DetailVideoReviewViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_detail_fragment
    private val args by navArgs<DetailFragmentArgs>()
    private val adapter = DetailAdapter()




    override fun initBinding(binding: LayoutDetailFragmentBinding) {
        super.initBinding(binding)

        vm.getAllDetail(args.movieId)

        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                binding.recyclerReview.visibility = View.GONE
                binding.noReview.visibility = View.GONE
                binding.retryReview.visibility = View.GONE
            } else if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.retryReview.visibility = View.VISIBLE
                binding.recyclerReview.visibility = View.GONE
                binding.noReview.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                binding.recyclerReview.visibility = View.GONE
                binding.noReview.visibility = View.VISIBLE
                binding.retryReview.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount != 0) {
                binding.recyclerReview.visibility = View.VISIBLE
                binding.noReview.visibility = View.GONE
                binding.retryReview.visibility = View.GONE
            }
        }
        binding.recyclerReview.adapter = adapter

        binding.retryButton.setOnClickListener {
            vm.getAllDetail(args.movieId)
        }

        observeResponseData(vm.getDetail,
            success = {
                binding.card.visibility = View.VISIBLE
                binding.poster.visibility = View.VISIBLE
                binding.loadingContainer.visibility = View.GONE
                binding.retryButton.visibility = View.GONE

                binding.data = it

                binding.chipGroup.removeAllViews()
                binding.data?.genres?.forEach {
                    binding.chipGroup.apply {
                        val chip = Chip(binding.root.context)

                        chip.text = it.name
                        chip.chipMinHeight = 50F
                        chip.marginTop
                        chip.setChipBackgroundColorResource(R.color.genre_chip_bg)
                        chip.shapeAppearanceModel.withCornerSize(12F)
                        chip.setTextAppearance(R.style.SmallerText)
                        chip.isClickable = false

                        addView(chip)

                    }
                }



            },
            error = {
                binding.card.visibility = View.GONE
                binding.poster.visibility = View.GONE
                binding.loadingContainer.visibility = View.GONE
                binding.retryButton.visibility = View.VISIBLE
            },
            loading = {
                binding.card.visibility = View.GONE
                binding.poster.visibility = View.GONE
                binding.loadingContainer.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
            })

        observeResponseData(vm.getVideo,
            success = { videoData ->
                binding.youtubePlayerView.visibility = View.VISIBLE

                val listener = object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        try {
                            super.onReady(youTubePlayer)
                            val videoId = videoData.results.filter { video ->
                                video.type == "Trailer" }[0].key
                            videoId.let { youTubePlayer.cueVideo(it, 0f) }

                            val defaultPlayerUiController =
                                DefaultPlayerUiController(binding.youtubePlayerView, youTubePlayer)
                            binding.youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

                        } catch (e: Exception) {
                            binding.youtubePlayerView.visibility = View.GONE
                        }
                    }
                }

                val option = IFramePlayerOptions.Builder().controls(0).build()
                binding.youtubePlayerView.initialize(listener, option)

            },
            error = {
                binding.youtubePlayerView.visibility = View.GONE
            },
            loading = {})

        vm.getReviewData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }


    }


}