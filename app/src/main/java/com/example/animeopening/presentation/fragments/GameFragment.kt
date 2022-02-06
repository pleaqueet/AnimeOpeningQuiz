package com.example.animeopening.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.animeopening.R
import com.example.animeopening.databinding.FragmentGameBinding
import java.util.*


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        val animationText: Animation = AnimationUtils.loadAnimation(context, R.anim.text_opening)
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)

        binding.clickText.startAnimation(animationText)


        binding.root.setOnClickListener {

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

}