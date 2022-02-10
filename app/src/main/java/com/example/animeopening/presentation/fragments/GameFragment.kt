package com.example.animeopening.presentation.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.animeopening.R
import com.example.animeopening.databinding.FragmentGameBinding
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.domain.models.Pack
import com.example.animeopening.presentation.OpeningsViewModel
import com.example.animeopening.util.Animator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GameFragment : Fragment() {
    private val viewModel by viewModels<OpeningsViewModel>()
    private lateinit var binding: FragmentGameBinding
    private lateinit var openingsOfCurrentPack: ArrayList<Opening>
    private lateinit var currentOpening: Opening
    private lateinit var currentPack: Pack
    private lateinit var mediaPlayer: MediaPlayer
    private var allOpenings = listOf<Opening>()
    private lateinit var navController: NavController
    private lateinit var animator: Animator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        animator = Animator(requireContext())
        currentPack = Pack(
            requireActivity().intent.getIntExtra("pack", 1),
            isDownloading = false,
            isPlayed = false
        )
        openingsOfCurrentPack =
            requireActivity().intent.getParcelableArrayListExtra<Opening>("openings") as ArrayList<Opening>
        currentPack.isPlayed = true
        viewModel.updatePack(currentPack)

        viewModel.openingsLiveData.observe(viewLifecycleOwner,
            { data ->
                allOpenings = data
            })

        binding.clickText.startAnimation(animator.animationTextOpening)

        binding.clickToSkip.isVisible = false
        binding.root.setOnClickListener {
            currentOpening = openingsOfCurrentPack.random()
            openingsOfCurrentPack.remove(currentOpening)

            when (currentOpening.difficulty) {
                1 -> {
                    binding.easyDiff.isVisible = true
                    binding.easyDiff.startAnimation(animator.animationTextOpening)
                    binding.pers.setBackgroundResource(R.drawable.ic_rori_easy)
                    binding.pers.startAnimation(animator.animationSlideUp)
                }
                2 -> {
                    binding.mediumDiff.isVisible = true
                    binding.mediumDiff.startAnimation(animator.animationTextOpening)
                    binding.pers.setBackgroundResource(R.drawable.ic_pers_medium)
                    binding.pers.startAnimation(animator.animationSlideUp)
                }
                3 -> {
                    binding.hardDiff.isVisible = true
                    binding.hardDiff.startAnimation(animator.animationTextOpening)
                    binding.pers.setBackgroundResource(R.drawable.ic_pers_hard)
                    binding.pers.startAnimation(animator.animationSlideUp)
                }
            }
            playOpening(currentOpening)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun playOpening(playingOpening: Opening) {
        val opUri = Uri.parse("${requireActivity().filesDir}/${playingOpening.mp3}")
        mediaPlayer = MediaPlayer.create(context, opUri)
        mediaPlayer.start()
        binding.root.isClickable = false
        binding.clickText.startAnimation(animator.animationTextEnd)
        binding.clickText.isVisible = false

        val timer = Timer()
        var time = 15
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                time--
                val bundle = bundleOfOpening(playingOpening)
                requireActivity().runOnUiThread { binding.timer.text = time.toString() }
                if (time == 14) {
                    requireActivity().runOnUiThread {
                        binding.clickToSkip.isVisible = true
                        binding.clickToSkip.startAnimation(animator.animationTextOpening)
                        binding.root.isClickable = true
                        binding.root.setOnClickListener {
                            cancel()
                            navController.navigate(
                                R.id.action_gameFragment_to_answerFragment,
                                bundle
                            )
                            mediaPlayer.reset()
                            mediaPlayer.stop()
                            binding.root.isClickable = false
                        }
                    }
                }
                if (time == 0) {
                    cancel()
                    requireActivity().runOnUiThread {
                        navController.navigate(
                            R.id.action_gameFragment_to_answerFragment,
                            bundle
                        )
                    }
                    mediaPlayer.reset()
                    mediaPlayer.stop()
                }
            }
        }, 1000, 1000)
    }

    private fun bundleOfOpening(opening: Opening): Bundle {
        val randomOpeningId1 = (allOpenings.indices - (opening.id - 1)).random()
        val randomOpeningId2 = (allOpenings.indices - (opening.id - 1) - randomOpeningId1).random()
        val randomOpeningId3 =
            (allOpenings.indices - (opening.id - 1) - randomOpeningId1 - randomOpeningId2).random()
        return bundleOf(
            "answerOpeningTitle" to opening.opening,
            "openingDiff" to opening.difficulty,
            "openingTitle1" to allOpenings[randomOpeningId1].opening,
            "openingTitle2" to allOpenings[randomOpeningId2].opening,
            "openingTitle3" to allOpenings[randomOpeningId3].opening,
            "isEndPack" to openingsOfCurrentPack.isEmpty()
        )
    }
}
