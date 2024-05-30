package com.roman.finalkinopoisk.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.roman.finalkinopoisk.domain.SearchFilmsUseCase
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.Search

class FilmsSearchPagingSource(
    private val searchFilmsUseCase: SearchFilmsUseCase,
    private val searchSetting:Search,
    private val keyword:String
): PagingSource<Int, Films>(){
    override fun getRefreshKey(state: PagingState<Int, Films>): Int? =FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Films> {
        val page=params.key?:FIRST_PAGE
        return  kotlin.runCatching {
            searchFilmsUseCase.execute(searchSetting,keyword,page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data=it.filmsList,
                    prevKey = null,
                    nextKey = if (it.totalPages==page) null else page+1
                )
            },
            onFailure = {
                LoadResult.Error(it)

            }

        )

    }
    private companion object{
        private const val FIRST_PAGE=1
    }
}