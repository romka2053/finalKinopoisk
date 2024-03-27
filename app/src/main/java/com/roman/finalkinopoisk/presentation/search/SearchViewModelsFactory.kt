package com.roman.finalkinopoisk.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.roman.finalkinopoisk.App
import com.roman.finalkinopoisk.data.room.FilmsDao
import com.roman.finalkinopoisk.domain.UseCase
import com.roman.finalkinopoisk.presentation.MainViewModel
import javax.inject.Inject



class SearchViewModelsFactory  @Inject constructor(
    private val useCase: UseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>,extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            return SearchViewModel(useCase) as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name ")
    }

}