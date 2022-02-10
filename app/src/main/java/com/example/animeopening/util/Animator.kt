package com.example.animeopening.util

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.animeopening.R

class Animator(context: Context) {
    val animationTextOpening: Animation = AnimationUtils.loadAnimation(context, R.anim.text_opening)
    val animationTextEnd: Animation = AnimationUtils.loadAnimation(context, R.anim.text_ending)
    val animationSlideUp: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
}