package com.example.foodapp

import com.example.foodapp.model.Food

object FoodGeneration {
    fun generateFood(): List<Food> {
        val foodList = mutableListOf<Food>()
        foodList.addAll(
            listOf(
                Food(
                    "Spagheti",
                    "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/preview",
                    5
                ),
                Food(
                    "Spagheti",
                    "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/preview",
                    5
                ),
                Food(
                    "Spagheti",
                    "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/preview",
                    5
                )
            )
        )
        return foodList.toList()
    }
}