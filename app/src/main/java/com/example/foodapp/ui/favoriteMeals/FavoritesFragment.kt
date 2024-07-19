package com.example.foodapp.ui.favoriteMeals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.databinding.FragmentFavoritesBinding
import com.example.foodapp.model.CategoryMeals
import com.example.foodapp.model.Meal
import com.example.foodapp.ui.categoryMealsDetail.CategoryMealListAdapter
import com.example.foodapp.ui.favoriteMeals.FavoriteMealsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavoritesFragment : Fragment() {
    lateinit var binding : FragmentFavoritesBinding
    lateinit var adapter : FavoriteMealsAdapter
    private val favoritesList = mutableListOf<CategoryMeals>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.favoriteMealsRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavoriteMealsAdapter(favoritesList, this::navigateToMealDetail)
        binding.favoriteMealsRecycler.adapter = adapter

        loadFavorites()
    }

    fun navigateToMealDetail(id:Int){
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToMealDetailFragment(id)
        findNavController().navigate(action)
    }

    private fun loadFavorites() {
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance("https://foodapp-616ab-default-rtdb.europe-west1.firebasedatabase.app").reference


        val userId = auth.currentUser?.uid
        if (userId != null) {
            val favoritesRef = database.child("favorites").child(userId)
            favoritesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    favoritesList.clear()
                    for (mealSnapshot in snapshot.children) {
                        val meal = mealSnapshot.getValue(CategoryMeals::class.java)
                        meal?.let { favoritesList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to load favorites", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}