package com.example.andersenhw.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenhw.databinding.ItemRvBinding

class DogsAdapter : ListAdapter<String, DogsAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding =
            ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    fun addItems(items: List<String?>) {
        submitList(items)
    }

    inner class ItemViewHolder(private val viewBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(itemPosition: Int) {
            getItem(itemPosition)?.let {
                viewBinding.dogFact.text = it
            }
        }

    }

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(old: String, new: String) = old === new
        override fun areContentsTheSame(old: String, new: String) = old == new
    }
}
