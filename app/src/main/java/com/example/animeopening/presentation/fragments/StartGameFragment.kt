package com.example.animeopening.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.animeopening.R
import com.example.animeopening.databinding.FragmentStartGameBinding

class StartGameFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentStartGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartGameBinding.inflate(inflater, container, false)
        val prefs = activity?.getSharedPreferences("score", 0)
        binding.stats.text = "Ваш счёт: ${
            prefs?.getInt(
                "score",
                0
            )
        }\nУгадано опенингов: ${prefs?.getInt("countOpenings", 0)}\nНажмите, чтобы начать игру"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.root.setOnClickListener {
            navController.navigate(R.id.action_startGameFragment_to_gameFragment)
        }
    }
}