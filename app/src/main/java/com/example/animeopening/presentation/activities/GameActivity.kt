package com.example.animeopening.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animeopening.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
    }
}