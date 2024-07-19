package com.example.foodapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.FoodListItemBinding
import com.example.foodapp.model.Food
import com.squareup.picasso.Picasso

class FoodListAdapter(val foodList : List<Food>):RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {
    class ViewHolder(val binding: FoodListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FoodListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.binding.foodName.text = food.name
        Picasso.get().load(food.image).into(holder.binding.foodImage)
    }
}