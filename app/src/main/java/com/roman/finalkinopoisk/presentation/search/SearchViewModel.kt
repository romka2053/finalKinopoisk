package com.roman.finalkinopoisk.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.roman.finalkinopoisk.data.FilmsSearchPagingSource
import com.roman.finalkinopoisk.domain.GetGenreAndCountryUseCase
import com.roman.finalkinopoisk.domain.SearchFilmsUseCase
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.GenreAndCountryList
import com.roman.finalkinopoisk.entity.Search
import com.roman.finalkinopoisk.presentation.StateLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getGenreAndCountryUseCase: GetGenreAndCountryUseCase,
    private val searchFilmsUseCase: SearchFilmsUseCase
    ) : ViewModel() {
    val typeCinema get() = _typeCinema
    val order get() = _order

    private lateinit  var _searchFilms: Flow<PagingData<Films>>
    val searchFilms get()=_searchFilms
    private val _stateLoading = MutableStateFlow<StateLoading>(StateLoading.Loading)
    val state = _stateLoading.asStateFlow()
    private lateinit var _genreAndCountry: GenreAndCountryList
    val genreAndCountry get() = _genreAndCountry
    suspend fun getGenresAndCounties() {
        _stateLoading.value = StateLoading.Loading
        _genreAndCountry = getGenreAndCountryUseCase.execute()
        _stateLoading.value = StateLoading.Success

    }




      fun newKey() {

        _searchFilms = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                FilmsSearchPagingSource(
                    searchFilmsUseCase,
                    Search(
                        typeCinema.type,
                        country,
                        genre,
                        yearFrom,
                        yearTo,
                        ratingFrom,
                        ratingTo,
                        order.sorted
                    ),
                    keyword
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun setCountries(listCountry: List<Int>) {
        _country = listCountry
    }
    fun setViewedFilms() {
        _noViewedFilms = !noViewedFilms
    }
    fun setGenres(listGenre: List<Int>) {
        _genre = listGenre
    }
    fun setSortBy(sortBy: SortBy){
        _order=sortBy
    }
    fun setYearFromAndTo(yearFrom:Int,yearTo:Int) {
        _yearFrom = yearFrom
        _yearTo = yearTo
    }
fun setRating(ratingFrom:Float,ratingTo:Float){
    _ratingFrom=ratingFrom
    _ratingTo=ratingTo
}
    fun setTypeCinema(typeCinema: TypeCinema){
        _typeCinema=typeCinema

    }
fun setKeyword(text:String)
{
    _keyword=text
}
    companion object{
        private var _keyword = ""
        val keyword get() = _keyword
        private var _typeCinema:TypeCinema =TypeCinema.ALL

        private var _yearFrom = 1800
        val yearFrom get() = _yearFrom
        private var _yearTo =   SimpleDateFormat("yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
            .toInt()
        val yearTo get() = _yearTo
        private var _ratingFrom = 0f
        val ratingFrom get() = _ratingFrom
        private var _ratingTo = 10f
        val ratingTo get() = _ratingTo
        private var _order : SortBy=SortBy.DATE

        private var _country: List<Int> = emptyList()
        val country get() = _country
        private var _genre: List<Int> = emptyList()
        val genre get() = _genre
        private var _noViewedFilms=false
        val noViewedFilms get() = _noViewedFilms
    }
    enum class TypeCinema(val type:String)
    {
        ALL("ALL"),FILMS("FILM"),SERIALS("TV_SERIES")
    }
    enum class SortBy(val sorted:String)
    {
        DATE("YEAR"),POPULAR("NUM_VOTE"),RATING("RATING")
    }
}