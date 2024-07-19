package com.example.foodapp.ui.mealDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.domain.CategoriesRepository
import com.example.foodapp.model.Food
import com.example.foodapp.model.Meal
import com.example.foodapp.model.MealResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {
    private val _meal = MutableLiveData<Meal>()
    private val repository = CategoriesRepository()
    val meal: MutableLiveData<Meal> get() = _meal
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite
    private val filteredMeal = MutableLiveData<Meal>()

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://foodapp-616ab-default-rtdb.europe-west1.firebasedatabase.app").reference


    fun getSingleMeal(id: Int?): MutableLiveData<Meal> {
        if (id != null) {
            repository.service.getSingleMeal(id).enqueue(object : retrofit2.Callback<MealResponse> {
                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                    if (response.body() != null) {
                        meal.postValue(response.body()!!.meals[0])
                        checkIfFavorite(response.body()!!.meals[0].idMeal.toString())
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    println(t.printStackTrace())
                }
            })
        }
        return meal
    }

    fun getFilteredMeal(mealName: String) : MutableLiveData<Meal>{
        repository.service.getFilteredMeal(mealName).enqueue(object : Callback<MealResponse>{
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                filteredMeal.postValue(response.body()!!.meals[0])
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                println(t.printStackTrace())
            }
        })
        return filteredMeal
    }
    fun checkIfFavorite(mealId: String) {
        val userId = auth.currentUser?.uid
        userId?.let {
            val favoriteRef = database.child("favorites").child(userId).child(mealId)
            favoriteRef.get().addOnSuccessListener { snapshot ->
                _isFavorite.value = snapshot.exists()
            }
        }
    }

    fun addToFavorites(meal: Meal) {
        val userId = auth.currentUser?.uid
        userId?.let {
            val favoritesRef = database.child("favorites").child(userId).child(meal.idMeal ?: "")
            favoritesRef.setValue(meal).addOnCompleteListener { task ->
                _isFavorite.value = task.isSuccessful
            }
        }
    }

    fun removeFromFavorites(mealId: String) {
        val userId = auth.currentUser?.uid
        userId?.let {
            val favoritesRef = database.child("favorites").child(userId).child(mealId)
            favoritesRef.removeValue().addOnCompleteListener { task ->
                _isFavorite.value = !task.isSuccessful
            }
        }
    }
}
