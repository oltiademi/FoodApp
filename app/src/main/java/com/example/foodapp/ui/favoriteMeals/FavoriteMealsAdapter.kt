package com.example.foodapp.ui.favoriteMeals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.FavoriteMealListItemBinding
import com.example.foodapp.databinding.MealListItemBinding
import com.example.foodapp.model.CategoryMeals
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class FavoriteMealsAdapter(
    private val mealList: MutableList<CategoryMeals>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<FavoriteMealsAdapter.ViewHolder>() {

    class ViewHolder(val binding: FavoriteMealListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FavoriteMealListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = mealList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = mealList[position]
        with(holder.binding) {
            mealName.text = meal.strMeal
            Picasso.get().load(meal.strMealThumb).into(mealImage)
            favoritesButton.setOnClickListener {
                removeMealFromFavorites(meal, position)
            }
        }
        holder.itemView.setOnClickListener {
            onClick(meal.idMeal!!.toInt())
        }
    }

    private fun removeMealFromFavorites(meal: CategoryMeals, position: Int) {
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance("https://foodapp-616ab-default-rtdb.europe-west1.firebasedatabase.app").reference
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val mealRef = database.child("favorites").child(userId).child(meal.idMeal.toString())
            mealRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mealList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                }
            }
        }
    }
}
