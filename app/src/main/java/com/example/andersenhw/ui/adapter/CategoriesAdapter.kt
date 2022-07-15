package com.example.andersenhw.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenhw.databinding.ItemCategoryBinding
import com.example.andersenhw.domain.models.Category

class CategoriesAdapter(
    private val onLoanClicked: (Category) -> Unit
): RecyclerView.Adapter<CategoriesAdapter.ListViewHolderCategories>() {

    var data = arrayListOf<Category>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setData(models: List<Category>) {
        data.clear()
        data.addAll(models)
        notifyDataSetChanged()
    }

    inner class ListViewHolderCategories(
        private val binding: ItemCategoryBinding,
        private val onClick: (Category) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: Category) {
            binding.categoryName.text = listItem.name
            binding.card.setOnClickListener {
                onClick(listItem.copy())
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolderCategories, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener { onLoanClicked(data[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolderCategories =
            ListViewHolderCategories(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false), onLoanClicked  )

    override fun getItemCount(): Int = data.size
}