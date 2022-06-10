package id.indocyber.themoviedbumair.fragment.genre

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.ui.BaseFragment
import id.indocyber.themoviedbumair.R
import id.indocyber.themoviedbumair.databinding.LayoutGenreFragmentBinding
import id.indocyber.themoviedbumair.view_model.GenreViewModel

@AndroidEntryPoint
class GenreFragment : BaseFragment<GenreViewModel, LayoutGenreFragmentBinding>() {
    override val vm: GenreViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.layout_genre_fragment
    private val adapter = GenreAdapter {
        vm.selectionTrack?.isSelected(it) ?: false
    }

    override fun initBinding(binding: LayoutGenreFragmentBinding) {
        super.initBinding(binding)
        binding.recyclerGenre.adapter = adapter
        createTracker()

        observeResponseData(vm.genreLiveData,
            success = {
                binding.recyclerGenre.visibility = View.VISIBLE
                binding.loadingContainer.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
                adapter.differ.submitList(it)
                vm.listGenre.addAll(it)
                if (vm.selectionTrack != null) {
                    binding.fabToMovie.visibility = View.VISIBLE
                } else {
                    binding.fabToMovie.visibility = View.GONE
                }
            }, error = {
                binding.retryButton.visibility = View.VISIBLE
                binding.loadingContainer.visibility = View.GONE
                binding.recyclerGenre.visibility = View.GONE
            }, loading = {
                binding.retryButton.visibility = View.GONE
                binding.loadingContainer.visibility = View.VISIBLE
                binding.recyclerGenre.visibility = View.GONE
            }
        )

        binding.retryButton.setOnClickListener {
            vm.getAllGenre()
        }

        binding.fabToMovie.setOnClickListener {
            vm.toMovie()
        }
    }

    private fun trackerBuilder() =
        SelectionTracker.Builder(
            "selectionId",
            binding.recyclerGenre,
            KeyProvider(adapter),
            ItemDetailLookup(binding.recyclerGenre),
            StorageStrategy.createLongStorage()
        ).withOnItemActivatedListener { a, _ ->
            a.selectionKey?.let {
                vm.selectionTrack?.select(it)
            }
            true

        }.withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()


    private fun createTracker() {
        vm.selectionTrack = vm.selectionTrack?.let {
            trackerBuilder().apply {
                it.selection.forEach {
                    this.select(it)
                }
            }

        } ?: kotlin.run {
            trackerBuilder()
        }
    }


}