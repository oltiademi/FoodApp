package com.example.foodapp.ui.categoryMealsDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.databinding.FragmentCategoryMealsBinding
import com.example.foodapp.ui.home.CategoryViewModel

class CategoryMealFragment: Fragment() {
    private lateinit var binding: FragmentCategoryMealsBinding
    val adapter = CategoryMealListAdapter(this::navigateToMealDetail)
    private val args : CategoryMealFragmentArgs by navArgs()
    private val viewModel : CategoryViewModel by viewModels<CategoryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryMealsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val categoryName = args.categoryName
        viewModel.getCategoryMeals(categoryName).observe(viewLifecycleOwner){
            if(it != null){
                adapter.mealList = it
            }
        }
        binding.categoryName.text = "$categoryName Category"
        binding.categoryMealsRecycler.adapter = adapter
        binding.categoryMealsRecycler.layoutManager = GridLayoutManager(requireActivity(), 2)
    }



    fun navigateToMealDetail(id:Int){
        val action = CategoryMealFragmentDirections.actionCategoryMealFragmentToMealDetailFragment(id)
        findNavController().navigate(action)
    }
}