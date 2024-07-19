package com.example.foodapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.FoodGeneration
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.model.Categories
import com.example.foodapp.model.Meal
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val adapter = CategoryListAdapter(this::navigateToMealDetail)
    private val categoryViewModel: CategoryViewModel by viewModels<CategoryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryViewModel._categories.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.categories = it
            }
        }

        categoryViewModel._randomMeal.observe(viewLifecycleOwner) { randomMeal ->
            if (randomMeal != null) {
                binding.randomMeal.foodName.text = randomMeal.strMeal
                Picasso.get().load(randomMeal.strMealThumb).into(binding.randomMeal.foodImage)
            }
        }

        binding.shuffleButton.setOnClickListener {
            categoryViewModel.getRandomMeal()
        }

        binding.categoryRecycler.adapter = adapter
        binding.categoryRecycler.layoutManager = GridLayoutManager(requireActivity(), 2)

    }

    fun navigateToMealDetail(category: Categories) {
        val categoryName : String = category.strCategory.toString()
        val action = HomeFragmentDirections.actionHomeFragmentToCategoryMealFragment(categoryName)
        findNavController().navigate(action)
    }
}