package com.example.foodapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.ui.categoryMealsDetail.CategoryMealFragmentDirections
import com.example.foodapp.ui.mealDetail.MealViewModel
import com.squareup.picasso.Picasso

class SearchFragment: Fragment() {
    lateinit var binding: FragmentSearchBinding
    val viewModel : MealViewModel by viewModels<MealViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.searchMeal.setOnClickListener {
            val mealName = binding.etSearch.text.toString()
            viewModel.getFilteredMeal(mealName).observe(viewLifecycleOwner) {meal->
                binding.foodName.text = meal.strMeal
                Picasso.get().load(meal.strMealThumb).into(binding.foodImage)

                binding.mealCard.visibility = View.VISIBLE
                binding.mealCard.setOnClickListener {
                    navigateToMealDetail(meal.idMeal!!.toInt())
                }
            }
        }
    }

    fun navigateToMealDetail(id: Int){
        val action = SearchFragmentDirections.actionSearchFragmentToMealDetailFragment(id)
        findNavController().navigate(action)
    }
}