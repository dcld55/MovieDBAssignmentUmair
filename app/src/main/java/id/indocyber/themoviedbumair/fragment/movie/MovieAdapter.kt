package id.indocyber.themoviedbumair.fragment.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import id.indocyber.common.entity.genre.Genre
import id.indocyber.common.entity.movie.Result
import id.indocyber.themoviedbumair.R
import id.indocyber.themoviedbumair.databinding.ItemLayoutMovieBinding


class MovieAdapter(
    private val genreName: Map<Int, Genre>,
    val movieToSend: (Int) -> Unit
) :

    PagingDataAdapter<Result, MovieViewHolder>(differ) {
    companion object {
        val differ = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Result,
                newItem: Result
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemLayoutMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.data = data
        holder.binding.chipgroup.removeAllViews()
        val genreData = data?.genre_ids?.map {
            genreName[it]?.name.orEmpty()
        }

        genreData?.forEach {

            holder.binding.chipgroup.apply {

                val chip = Chip(holder.binding.root.context)
                chip.text = it
                chip.chipMinHeight = 50F
                chip.marginTop
                chip.setChipBackgroundColorResource(R.color.genre_chip_bg)
                chip.shapeAppearanceModel.withCornerSize(12F)
                chip.setTextAppearance(R.style.SmallerText)
                chip.isClickable = false

                addView(chip)
            }
        }

        holder.binding.root.setOnClickListener {
            movieToSend(data?.id ?: 0)
        }
    }

}

class MovieViewHolder(val binding: ItemLayoutMovieBinding) : RecyclerView.ViewHolder(binding.root)
