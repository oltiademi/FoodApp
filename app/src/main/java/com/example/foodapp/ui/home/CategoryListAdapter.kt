package com.example.foodapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.CategoryListItemBinding
import com.example.foodapp.model.Categories
import com.squareup.picasso.Picasso

class CategoryListAdapter(val onClick: (Categories)->Unit):RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    var categories: List<Categories> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: CategoryListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]

        holder.binding.categoryName.text = category.strCategory
        holder.binding.categoryDescription.text = category.strCategoryDescription
        Picasso.get().load(category.strCategoryThumb).into(holder.binding.categoryImage)
        holder.itemView.setOnClickListener {
            onClick(category)
        }
    }
}