package com.roman.finalkinopoisk.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.roman.finalkinopoisk.domain.UseCase
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.presentation.search.SearchViewModel

class FilmsSearchPagingSource(
    private val useCase: UseCase
): PagingSource<Int, Films>(){
    override fun getRefreshKey(state: PagingState<Int, Films>): Int? =FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Films> {
        val page=params.key?:FIRST_PAGE
        return  kotlin.runCatching {
            useCase.searchFilms(SearchViewModel.searchSetting,SearchViewModel.keyword,page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data=it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page+1
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