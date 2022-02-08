package com.example.animeopening.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeopening.databinding.ActivityMainBinding
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.domain.models.Pack
import com.example.animeopening.presentation.OpeningsViewModel
import com.example.animeopening.presentation.adapters.PackClickListener
import com.example.animeopening.presentation.adapters.PacksAdapter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


const val STORAGE_URL = "gs://anime-opening-quiz.appspot.com"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PackClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var openings: List<Opening>
    private lateinit var packs: ArrayList<Pack>
    private val viewModel: OpeningsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = Firebase.storage(STORAGE_URL)
        openings = emptyList()
        packs = ArrayList()

        viewModel.packsLiveData.observe(this, { data ->
            for(i in data) {
                packs.add(i)
            }
        })

        viewModel.openingsLiveData.observe(this, { data ->
            openings = data
            binding.packsRecView.adapter = PacksAdapter(this, packs, openings, this)
        })

        binding.packsRecView.layoutManager = LinearLayoutManager(this)
    }

    override fun onPackClickListener(position: Int) {
        checkPack(position + 1, position * 10..position * 10 + 9)
    }

    private fun checkPack(pack: Int, openingNumbers: IntRange) {
        if (File(filesDir, openings[pack * 10 - 5].mp3).exists()) {
            val intent = Intent(this, GameActivity::class.java)
            val ops = ArrayList<Opening>()
            for (i in openingNumbers) {
                ops.add(openings[i])
            }
            intent.putExtra("pack", pack)
            intent.putParcelableArrayListExtra("openings", ops as ArrayList<out Parcelable?>?)
            startActivity(intent)
        } else {
            packs[pack - 1].isDownloading = true
            binding.packsRecView.adapter = PacksAdapter(this, packs, openings, this)
            for (openingNumber in openingNumbers) {
                downloadPack(pack, openingNumber)
            }
        }
    }

    private fun downloadPack(packNumber: Int, openingNumber: Int) {
        storage = Firebase.storage(STORAGE_URL)
        val filesReference =
            storage.getReferenceFromUrl("$STORAGE_URL/$packNumber/${openings[openingNumber].mp3}")
        val localFile = File(filesDir, openings[openingNumber].mp3)
        filesReference.getFile(localFile).addOnCompleteListener {
            packs[packNumber - 1].isDownloading = false
            binding.packsRecView.adapter = PacksAdapter(this, packs, openings, this)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}