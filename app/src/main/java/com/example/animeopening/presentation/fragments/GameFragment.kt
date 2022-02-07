package com.example.animeopening.presentation.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.animeopening.R
import com.example.animeopening.databinding.FragmentGameBinding
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.presentation.OpeningsViewModel
import com.example.animeopening.presentation.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GameFragment : Fragment() {
    private val viewModel by viewModels<OpeningsViewModel>()
    private lateinit var binding: FragmentGameBinding
    private lateinit var openings: ArrayList<Opening>
    private lateinit var opening: Opening
    private var allOpenings = listOf<Opening>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        val animationText: Animation = AnimationUtils.loadAnimation(context, R.anim.text_opening)
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)

        openings =
            requireActivity().intent.getParcelableArrayListExtra<Opening>("openings") as ArrayList<Opening>

        if (openings.size == 0) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }

        viewModel.openingsLiveData.observe(viewLifecycleOwner,
            {
                allOpenings = it
            })

        binding.clickText.startAnimation(animationText)

        binding.clickToSkip.isVisible = false
        binding.root.setOnClickListener {
            val randomOpeningId = (0 until openings.size).random()
            opening = openings[randomOpeningId]
            openings.remove(opening)

            when (opening.difficulty) {
                1 -> {
                    binding.easyDiff.isVisible = true
                    binding.easyDiff.startAnimation(animationText)
                    binding.pers.setBackgroundResource(R.drawable.ic_pers_easy)
                    binding.pers.startAnimation(animation)
                }
                2 -> {
                    binding.mediumDiff.isVisible = true
                    binding.mediumDiff.startAnimation(animationText)
                    binding.pers.setBackgroundResource(R.drawable.ic_pers_medium)
                    binding.pers.startAnimation(animation)
                }
                3 -> {
                    binding.hardDiff.isVisible = true
                    binding.hardDiff.startAnimation(animationText)
                    binding.pers.setBackgroundResource(R.drawable.ic_pers_hard)
                    binding.pers.startAnimation(animation)
                }
            }
            playOpening(opening)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun playOpening(op: Opening) {
        val animationOp: Animation = AnimationUtils.loadAnimation(context, R.anim.text_opening)
        val animationEnd: Animation = AnimationUtils.loadAnimation(context, R.anim.text_ending)
        val opUri = Uri.parse("${requireActivity().filesDir}/${op.mp3}")
        val mp = MediaPlayer.create(context, opUri)
        mp.start()
        binding.root.isClickable = false
        binding.clickText.startAnimation(animationEnd)
        binding.clickText.isVisible = false
        Log.e("ADWAWDA", op.id.toString())
        Log.e("ADWAWDA", (allOpenings.indices - op.id).toString())
        val opening1 = allOpenings[(allOpenings.indices - op.id+1).random()]
        Log.e("ADWAWDA", opening1.id.toString())
        Log.e("ADWAWDA", (allOpenings.indices - op.id - opening1.id).toString())
        val opening2 = allOpenings[(allOpenings.indices - op.id - opening1.id+1).random()]
        Log.e("ADWAWDA", opening2.id.toString())
        Log.e("ADWAWDA", (allOpenings.indices - op.id - opening1.id - opening2.id).toString())
        val opening3 = allOpenings[(allOpenings.indices - op.id - opening1.id - opening2.id+1).random()]
        Log.e("ADWAWDA", opening3.id.toString())
        val timer = Timer()
        var time = 15
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                time--
                val bundle = bundleOf(
                    "answerOpeningTitle" to op.opening,
                    "openingDiff" to op.difficulty,
                    "openingTitle1" to opening1.opening,
                    "openingTitle2" to opening2.opening,
                    "openingTitle3" to opening3.opening
                )
                activity?.runOnUiThread { binding.timer.text = time.toString() }
                if (time == 14) {
                    activity?.runOnUiThread {
                        binding.clickToSkip.isVisible = true
                        binding.clickToSkip.startAnimation(animationOp)
                        binding.root.isClickable = true
                        binding.root.setOnClickListener {
                            cancel()
                            navController.navigate(
                                R.id.action_gameFragment_to_answerFragment,
                                bundle
                            )
                            mp.reset()
                            mp.stop()
                            binding.root.isClickable = false
                        }
                    }
                }
                if (time == 0) {
                    cancel()
                    activity?.runOnUiThread {
                        navController.navigate(
                            R.id.action_gameFragment_to_answerFragment,
                            bundle
                        )
                    }
                    mp.reset()
                    mp.stop()
                }
            }
        }, 1000, 1000)
    }
}