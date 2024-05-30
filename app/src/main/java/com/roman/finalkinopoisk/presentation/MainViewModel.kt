package com.roman.finalkinopoisk.presentation

import androidx.lifecycle.ViewModel
import com.roman.finalkinopoisk.domain.AddCollectionToRoomUseCase
import com.roman.finalkinopoisk.domain.GetAllCollectionAndCountFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.roman.finalkinopoisk.entity.room.CollectionRoom

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCollectionAndCountFilmsUseCase: GetAllCollectionAndCountFilmsUseCase
) : ViewModel() {


    suspend fun getCollections():List<CollectionRoom>{
        return getAllCollectionAndCountFilmsUseCase.execute()
    }






}