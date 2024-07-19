package com.example.foodapp.ui.categoryMealsDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.FragmentCategoryMealsBinding
import com.example.foodapp.databinding.MealListItemBinding
import com.example.foodapp.model.CategoryMeals
import com.example.foodapp.model.Meal
import com.squareup.picasso.Picasso

class CategoryMealListAdapter(val onClick:(Int)->Unit): RecyclerView.Adapter<CategoryMealListAdapter.ViewHolder>() {

    var mealList : List<CategoryMeals> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: MealListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MealListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = mealList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = mealList[position]
        with(holder.binding){
            mealName.text = meal.strMeal
            Picasso.get().load(meal.strMealThumb).into(mealImage)
        }
        holder.itemView.setOnClickListener {
            onClick(meal.idMeal!!.toInt())
        }
    }
}