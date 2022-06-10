package id.indocyber.themoviedbumair.fragment.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.review.Result
import id.indocyber.themoviedbumair.databinding.ItemLayoutReviewBinding

class DetailAdapter : PagingDataAdapter<Result, ReviewViewHolder>(differ) {

    companion object {
        val differ = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            ItemLayoutReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class ReviewViewHolder(val binding: ItemLayoutReviewBinding) :
    RecyclerView.ViewHolder(binding.root)