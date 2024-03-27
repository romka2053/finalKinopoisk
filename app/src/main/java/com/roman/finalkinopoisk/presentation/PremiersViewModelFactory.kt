package com.roman.finalkinopoisk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roman.finalkinopoisk.domain.UseCase
import javax.inject.Inject

class PremiersViewModelFactory @Inject constructor(
    private val useCase: UseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(useCase) as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name ")
    }

}