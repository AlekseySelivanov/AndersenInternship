package com.example.andersenhw.presentation.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenhw.R
import com.example.andersenhw.data.model.CatFact
import com.example.andersenhw.databinding.ItemImageBinding
import com.squareup.picasso.Picasso

class ImagesAdapter(
    private var images: List<String> = listOf()
) : PagingDataAdapter<CatFact,ImagesAdapter.ItemViewHolder>(DiffCallback) {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: List<String>) {
        this.images = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)

    }

    override fun onBindViewHolder(parent: ItemViewHolder, position: Int) {
        parent.bind(images[position])
        val item = getItem(position)
        if (item != null) {
            parent.viewBinding.catFact.text = item.fact
        }
    }

    inner class ItemViewHolder(val viewBinding: ItemImageBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(urlImage: String) {
            Picasso.get()
                .load(urlImage)
                .resize(200, 200)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(viewBinding.ivImage)
        }
    }
    object DiffCallback : DiffUtil.ItemCallback<CatFact>() {
        override fun areItemsTheSame(old: CatFact, new: CatFact) = old == new
        override fun areContentsTheSame(old: CatFact, new: CatFact) = old.fact == new.fact
    }
}
