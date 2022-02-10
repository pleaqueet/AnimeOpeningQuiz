package com.example.animeopening.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.animeopening.R
import com.example.animeopening.databinding.FragmentAnswerBinding

class AnswerFragment : Fragment() {
    private var navController: NavController? = null
    private lateinit var binding: FragmentAnswerBinding
    lateinit var answerOpeningTitle: String
    var isEndPack: Boolean = false
    var diff = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnswerBinding.inflate(inflater, container, false)

        answerOpeningTitle = arguments?.getString("answerOpeningTitle").toString()
        diff = arguments?.getInt("openingDiff")!!
        val OpeningTitle1 = arguments?.getString("openingTitle1").toString()
        val OpeningTitle2 = arguments?.getString("openingTitle2").toString()
        val OpeningTitle3 = arguments?.getString("openingTitle3").toString()
        isEndPack = arguments?.getBoolean("isEndPack")!!

        val answer = (1..4).random()
        when (answer) {
            1 -> {
                binding.answerButton1.text = answerOpeningTitle
                binding.answerButton2.text = OpeningTitle1
                binding.answerButton3.text = OpeningTitle2
                binding.answerButton4.text = OpeningTitle3
            }
            2 -> {
                binding.answerButton1.text = OpeningTitle1
                binding.answerButton2.text = answerOpeningTitle
                binding.answerButton3.text = OpeningTitle2
                binding.answerButton4.text = OpeningTitle3
            }
            3 -> {
                binding.answerButton1.text = OpeningTitle2
                binding.answerButton2.text = OpeningTitle1
                binding.answerButton3.text = answerOpeningTitle
                binding.answerButton4.text = OpeningTitle3
            }
            4 -> {
                binding.answerButton1.text = OpeningTitle3
                binding.answerButton2.text = OpeningTitle1
                binding.answerButton3.text = OpeningTitle2
                binding.answerButton4.text = answerOpeningTitle
            }
        }

        binding.answerButton1.setOnClickListener {
            if (answerOpeningTitle == binding.answerButton1.text) {
                onWin()
            } else {
                onLose()
            }
        }
        binding.answerButton2.setOnClickListener {
            if (answerOpeningTitle == binding.answerButton2.text) {
                onWin()
            } else {
                onLose()
            }
        }
        binding.answerButton3.setOnClickListener {
            if (answerOpeningTitle == binding.answerButton3.text) {
                onWin()
            } else {
                onLose()
            }
        }
        binding.answerButton4.setOnClickListener {
            if (answerOpeningTitle == binding.answerButton4.text) {
                onWin()
            } else {
                onLose()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun onWin() {
        val bundle = bundleOf("result" to 1, "diff" to diff, "isEndPack" to isEndPack)
        try {
            navController?.navigate(R.id.action_answerFragment_to_resultFragment, bundle)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    private fun onLose() {
        val bundle = bundleOf("result" to 0, "title" to answerOpeningTitle, "isEndPack" to isEndPack)
        try {
            navController?.navigate(R.id.action_answerFragment_to_resultFragment, bundle)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}