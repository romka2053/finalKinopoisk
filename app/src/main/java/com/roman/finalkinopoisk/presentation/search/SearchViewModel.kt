package com.roman.finalkinopoisk.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.roman.finalkinopoisk.data.FilmsSearchPagingSource
import com.roman.finalkinopoisk.domain.UseCase
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: UseCase,

) : ViewModel() {



    var searchFilms: Flow<PagingData<Films>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { FilmsSearchPagingSource(useCase) }
    ).flow.cachedIn(viewModelScope)

     fun newKey(){
         searchFilms= Pager(
             config = PagingConfig(pageSize = 20),
             pagingSourceFactory = { FilmsSearchPagingSource(useCase ) }
         ).flow.cachedIn(viewModelScope)
     }

    companion object {

        var searchSetting = Search(
            typeCinema = "ALL",
            yearFrom = 1000,
            yearTo = 3000,
            ratingFrom = 0f,
            ratingTo = 10f,
            order = ""
        )

        var keyword:String=""
        fun searchSettingChange(searchSettingNew: Search) {
            searchSetting = searchSettingNew
        }
    }


}