package id.indocyber.themoviedbumair.fragment.movie

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.ui.BaseFragment
import id.indocyber.themoviedbumair.R
import id.indocyber.themoviedbumair.databinding.LayoutMovieFragmentBinding
import id.indocyber.themoviedbumair.view_model.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : BaseFragment<MovieViewModel, LayoutMovieFragmentBinding>() {
    override val vm: MovieViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_movie_fragment
    private val args by navArgs<MovieFragmentArgs>()
    private val adapter by lazy {
        MovieAdapter(args.genrename.associateBy {
            it.id
        }) {
            vm.toDetail(it)
        }
    }

    override fun initBinding(binding: LayoutMovieFragmentBinding) {
        super.initBinding(binding)
        binding.recyclerMovie.adapter = adapter



        vm.moviePagingLiveData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }

        vm.getMovie(args.genrelist)


        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                binding.recyclerMovie.visibility = View.GONE
                binding.noMovie.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
                binding.loadingContainer.visibility = View.VISIBLE
            } else if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.retryButton.visibility = View.VISIBLE
                binding.recyclerMovie.visibility = View.GONE
                binding.noMovie.visibility = View.GONE
                binding.loadingContainer.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                binding.recyclerMovie.visibility = View.GONE
                binding.noMovie.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
                binding.loadingContainer.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount != 0) {
                binding.recyclerMovie.visibility = View.VISIBLE
                binding.noMovie.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
                binding.loadingContainer.visibility = View.GONE
            }

        }
        binding.retryButton.setOnClickListener {
            adapter.retry()
        }
    }
}