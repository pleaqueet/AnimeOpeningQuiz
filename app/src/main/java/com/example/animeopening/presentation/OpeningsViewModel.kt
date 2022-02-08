package com.example.animeopening.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeopening.domain.models.Opening
import com.example.animeopening.domain.models.Pack
import com.example.animeopening.domain.repository.OpeningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpeningsViewModel
@Inject constructor(
    private val openingRepository: OpeningRepository
) : ViewModel() {

    val openingsLiveData: LiveData<List<Opening>> = openingRepository.getOpenings()
    val packsLiveData: LiveData<List<Pack>> = openingRepository.getPacks()

    fun updatePack(pack: Pack){
        viewModelScope.launch(Dispatchers.IO) {
            openingRepository.updatePack(pack)
        }
    }

}