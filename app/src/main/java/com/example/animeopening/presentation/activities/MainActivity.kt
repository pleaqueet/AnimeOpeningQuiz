package com.example.animeopening.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeopening.R
import com.example.animeopening.databinding.ActivityMainBinding
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.presentation.OpeningsViewModel
import com.example.animeopening.presentation.adapters.PacksAdapter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

const val STORAGE_URL = "gs://anime-opening-quiz.appspot.com"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PacksAdapter.PackClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var openings: List<Opening>
    private lateinit var packs: List<String>
    private val viewModel: OpeningsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = Firebase.storage(STORAGE_URL)
        openings = emptyList()
        packs = resources.getStringArray(R.array.packs).toList()
        binding.packsRecView.layoutManager = LinearLayoutManager(this)
        viewModel.openingsLiveData.observe(this, {
            openings = it
            binding.packsRecView.adapter = PacksAdapter(this, packs, openings, this)
        })
    }

    override fun onPackClickListener(position: Int) {
        when (position) {
            0 -> {
                val localFile = File(filesDir, openings[0].mp3)
                if (localFile.exists()) {
                    startActivity(Intent(this, GameActivity::class.java))
                } else {
                    for (openingNumber in 0..9) {
                        downloadPack(1, openingNumber)
                    }
                }
            }
            1 -> {
                for (openingNumber in 10..19) {
                    downloadPack(2, openingNumber)
                }
            }
            else -> print(1)
        }
    }

    private fun downloadPack(packNumber: Int, openingNumber: Int) {
        storage = Firebase.storage(STORAGE_URL)
        val filesReference =
            storage.getReferenceFromUrl("$STORAGE_URL/$packNumber/${openings[openingNumber].mp3}")
        val localFile = File(filesDir, openings[openingNumber].mp3)
        filesReference.getFile(localFile).addOnProgressListener { task ->
            if (task.bytesTransferred == task.totalByteCount) {

            }
        }
    }

    override fun onBackPressed() {
    }
}

