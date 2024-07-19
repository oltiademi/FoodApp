package com.example.foodapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.domain.CategoriesRepository
import com.example.foodapp.model.Categories
import com.example.foodapp.model.CategoryMealResponse
import com.example.foodapp.model.CategoryMeals
import com.example.foodapp.model.CategoryResponse
import com.example.foodapp.model.Meal
import com.example.foodapp.model.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel: ViewModel() {
    val repository = CategoriesRepository()
    val _categories = MutableLiveData<List<Categories>>()
    val meals = MutableLiveData<ArrayList<CategoryMeals>>()
    val _randomMeal = MutableLiveData<Meal>()

    init {
        getCategories()
        getRandomMeal()
    }

     fun getCategories() : MutableLiveData<List<Categories>> {
        repository.service.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                _categories.postValue(response.body()?.categories)
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                println(t.printStackTrace())
            }

        })
         return _categories
    }

    fun getCategoryMeals(categoryName: String): MutableLiveData<ArrayList<CategoryMeals>>{
        repository.service.getCategoryMeals(categoryName).enqueue(object : Callback<CategoryMealResponse>{
            override fun onResponse(call: Call<CategoryMealResponse>, response: Response<CategoryMealResponse>) {
                meals.postValue(response.body()?.meals)
            }

            override fun onFailure(call: Call<CategoryMealResponse>, t: Throwable) {
                println(t.printStackTrace())
            }
        })
        return meals
    }

    fun getRandomMeal() : MutableLiveData<Meal>{
        repository.service.getRandomMeal().enqueue(object : Callback<MealResponse>{
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                _randomMeal.postValue(response.body()?.meals?.get(0))
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                println(t.printStackTrace())
            }

        })
        return _randomMeal
    }
}