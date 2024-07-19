package com.example.foodapp.ui.mealDetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentMealDetailBinding
import com.example.foodapp.model.Meal
import com.squareup.picasso.Picasso

class MealDetailFragment : Fragment() {
    private lateinit var binding: FragmentMealDetailBinding
    private val viewModel: MealViewModel by viewModels()
    private val args: MealDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealId = args.mealId

        viewModel.getSingleMeal(mealId)
        viewModel.meal.observe(viewLifecycleOwner) { meal ->
            Picasso.get().load(meal.strMealThumb).into(binding.mealImage)
            binding.mealName.text = meal.strMeal
            binding.mealLocationName.text = meal.strArea
            binding.mealInstructions.text = meal.strInstructions
            val mediaItem = MediaItem.fromUri(meal.strYoutube.toString())
            val player = ExoPlayer.Builder(requireActivity()).build()
            binding.video.player = player
            player.setMediaItem(mediaItem)
            player.volume = 0f
            player.prepare()
            player.play()
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteButton(isFavorite)
        }

        binding.addToFavorites.setOnClickListener {
            viewModel.isFavorite.value?.let { isFavorite ->
                if (isFavorite) {
                    viewModel.removeFromFavorites(mealId.toString())
                } else {
                    viewModel.meal.value?.let { meal ->
                        viewModel.addToFavorites(meal)
                    }
                }
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.addToFavorites.background = resources.getDrawable(R.drawable.already_favorite_background)
            binding.addToFavorites.setColorFilter(Color.RED)
        } else {
            binding.addToFavorites.background = resources.getDrawable(R.drawable.ic_heart_background)
            binding.addToFavorites.setColorFilter(Color.WHITE)
        }
    }
}
