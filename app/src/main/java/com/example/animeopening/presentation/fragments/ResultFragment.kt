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
import com.example.animeopening.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private lateinit var navController: NavController
    var countOpenings = 0
    var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        val prefs = activity?.getSharedPreferences("score", 0)
        score = prefs?.getInt("score", 0)!!
        countOpenings = prefs.getInt("countOpenings", 0)

        val result = arguments?.getInt("result")
        val title = arguments?.getString("title")
        val diff = arguments?.getInt("diff")
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.text_opening)
        if (result == 1) {
            score += diff!!
            countOpenings += 1
            val prefs = activity?.getSharedPreferences("score", 0)
            val editor = prefs?.edit()
            editor?.putInt("score", score)
            editor?.putInt("countOpenings", countOpenings)
            editor?.apply()
            binding.resultText.text =
                "Верный ответ!\nВаш счёт: ${
                    prefs?.getInt(
                        "score",
                        0
                    )
                }\nУгадано опенингов: ${
                    prefs?.getInt(
                        "countOpenings",
                        0
                    )
                }\nНажмите, чтобы продолжить"
            binding.resultText.startAnimation(animation)
            binding.root.isClickable = true
            binding.root.setOnClickListener {
                navController.navigate(R.id.action_resultFragment_to_gameFragment)
            }
        } else {
            binding.resultText.text =
                "Неверный ответ\nПравильный ответ: $title\nВаш счёт: ${
                    prefs?.getInt(
                        "score",
                        0
                    )
                }\nУгадано опенингов: ${
                    prefs?.getInt(
                        "countOpenings",
                        0
                    )
                }\nНажмите, чтобы продолжить"
            binding.root.isClickable = true
            binding.resultText.startAnimation(animation)
            binding.root.isClickable = true
            binding.root.setOnClickListener {
                navController.navigate(R.id.action_resultFragment_to_gameFragment)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
}