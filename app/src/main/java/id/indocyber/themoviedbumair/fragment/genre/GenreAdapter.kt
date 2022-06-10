package id.indocyber.themoviedbumair.fragment.genre

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.genre.Genre
import id.indocyber.themoviedbumair.databinding.ItemLayoutGenreBinding

class GenreAdapter(
    val isSelected: (Long) -> Boolean
) : RecyclerView.Adapter<GenreViewHolder>() {

    val differ = AsyncListDiffer(this, differGenres)

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(
            ItemLayoutGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), differ
        )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.data = data
        holder.binding.isSelected = isSelected(data.id.toLong())


    }

    override fun getItemCount(): Int = differ.currentList.size

    companion object {
        val differGenres = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun getItemId(position: Int): Long = differ.currentList[position].id.toLong()

}

class KeyProvider(private val adapter: GenreAdapter) : ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long {
        return adapter.differ.currentList[position].id.toLong()
    }

    override fun getPosition(key: Long): Int {
        return adapter.differ.currentList.indexOfFirst {
            it.id.toLong() == key
        }
    }

}

class ItemDetailLookup(val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        return recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as GenreViewHolder).getItemDetails()
        }
    }

}

class GenreViewHolder(val binding: ItemLayoutGenreBinding, val differ: AsyncListDiffer<Genre>) :
    RecyclerView.ViewHolder(binding.root) {
    fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getPosition(): Int = absoluteAdapterPosition

        override fun getSelectionKey(): Long =
            differ.currentList[absoluteAdapterPosition].id.toLong()

    }
}
