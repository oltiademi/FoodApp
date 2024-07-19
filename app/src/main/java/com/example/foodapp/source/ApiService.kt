package com.example.foodapp.source

import com.example.foodapp.model.CategoryMealResponse
import com.example.foodapp.model.CategoryResponse
import com.example.foodapp.model.Food
import com.example.foodapp.model.Meal
import com.example.foodapp.model.MealResponse
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/json/v1/1/categories.php")
    fun getCategories() : Call<CategoryResponse>

    @GET("/api/json/v1/1/filter.php")
    fun getCategoryMeals(@Query("c") categoryName: String) : Call<CategoryMealResponse>

    @GET("/api/json/v1/1/lookup.php")
    fun getSingleMeal(@Query("i") id:Int) : Call<MealResponse>

    @GET("/api/json/v1/1/random.php")
    fun getRandomMeal() : Call<MealResponse>

    @GET("/api/json/v1/1/search.php")
    fun getFilteredMeal(@Query("s") mealName: String): Call<MealResponse>
}