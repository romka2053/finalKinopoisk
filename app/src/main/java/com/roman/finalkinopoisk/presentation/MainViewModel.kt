package com.roman.finalkinopoisk.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.roman.finalkinopoisk.domain.UseCase
import com.roman.finalkinopoisk.entity.*
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import com.roman.finalkinopoisk.entity.room.FilmCollection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {
    private var _stateLoading = MutableStateFlow<StateLoading>(StateLoading.GetInfoFilm)
    val state = _stateLoading.asStateFlow()
lateinit var filmById:FullInformationFilm
lateinit var staffById:StaffWithFilms
    suspend fun getFilmById(id: Int) {
            _stateLoading.value = StateLoading.GetInfoFilm
            filmById= useCase.getFilmById(id)
            _stateLoading.value = StateLoading.Success
    }
suspend fun addFilmCollection(filmCollection:FilmCollection){
    useCase.addFilmCollection(filmCollection)
}
    suspend fun deleteFilmCollection(filmCollection: FilmCollection){
        useCase.deleteFilmCollection(filmCollection)
    }
suspend fun getStaffById(id:Int) {
    _stateLoading.value = StateLoading.GetInfoFilm
     staffById=useCase.getStaffById(id)
    _stateLoading.value=StateLoading.Success
}
    suspend fun getListFilmByListId(list: List<Int>):List<FilmFull>{
       return useCase.getListFilmsByListId(list)
    }
    suspend fun addCollectionToRoom(collection: CollectionRoom):Long{
        return useCase.addCollectionToRoom(collection)
    }
    suspend fun getAllCollection():List<CollectionRoom>{
        return useCase.getAllCollection()
    }
    suspend fun getSimilarsById(id: Int): FilmsList {
        return useCase.getSimilarsById(id)
    }


    suspend fun getAllCategoryFilms(categoryGetFilms: List<SettingGetFilms>): List<CategoryFilms> {
        val categoryListFilms = mutableListOf<CategoryFilms>()
        categoryGetFilms.forEach { item ->
            when (item) {
                is SettingGetFilms.PremiersGetSetting -> {
                    categoryListFilms.add(
                        CategoryFilms(
                            useCase.getAllPremiers(item.year, item.month),
                            item.name
                        )
                    )
                }
                is SettingGetFilms.TopPopularGetSetting -> {
                    categoryListFilms.add(
                        CategoryFilms(
                            useCase.getAllPopular(item.page),
                            item.name
                        )
                    )
                }
                is SettingGetFilms.Top250GetSetting -> {
                    categoryListFilms.add(
                        CategoryFilms(
                            useCase.getTop250Movies(item.page),
                            item.name
                        )
                    )
                }
            }
        }

        return categoryListFilms
    }



}