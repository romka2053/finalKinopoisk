package com.roman.finalkinopoisk.domain

import android.util.Log
import com.roman.finalkinopoisk.data.*
import com.roman.finalkinopoisk.entity.*
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import com.roman.finalkinopoisk.entity.room.FilmCollection
import com.roman.finalkinopoisk.presentation.ProfessionsKey
import com.roman.finalkinopoisk.presentation.StateLoading
import javax.inject.Inject

class UseCase @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val staffRepository: StaffRepository,
    private val filmsRepository: FilmsRepository,
) {
    suspend fun getStaffById(id:Int): StaffWithFilms {
        val staff=staffRepository.getStaffById(id)
        val films=staff.films.sortedByDescending { it.rating }
        val size = if (films.size >= 10) 10 else films.size

        val filmIdInStaffPopular=List(size){item-> filmsRepository.getFilmById1(films[item].filmId).filmRoom}


        val mapKeyProfession = mutableMapOf<ProfessionsKey,MutableList<FilmsInStaff>>()
            ProfessionsKey.values().forEach {
                mapKeyProfession[it]= mutableListOf()
            }

        staff.films.forEach {

            try {
                mapKeyProfession[ProfessionsKey.valueOf(it.professionKey)]?.add(it)
            }catch (e:Exception){
                mapKeyProfession[ProfessionsKey.UNKNOWN]?.add(it)
            }
        }
            mapKeyProfession.forEach{

            }
        val sortedMap=mapKeyProfession.toList()
            .sortedByDescending { (_, values) -> values.size }.toMap()
            .filterValues { v-> v!= emptyList<FilmsInStaff>() }
        Log.d("1111",sortedMap.keys.toString())
        return StaffWithFilms(staff,FilmsList(size,filmIdInStaffPopular),sortedMap)
    }
    suspend fun addCollectionToRoom(collection: CollectionRoom):Long {
        return filmsRepository.addCollectionToRoom(collection)
    }
    suspend fun addFilmCollection(filmCollection: FilmCollection){
        filmsRepository.addFilmCollection(filmCollection)
    }
    suspend fun deleteFilmCollection(filmCollection: FilmCollection){
        filmsRepository.deleteFilmCollection(filmCollection)
    }
    suspend fun getAllCollection():List<CollectionRoom>{
        return filmsRepository.getAllCollection()
    }
    suspend fun searchFilms(search:Search,keyword:String,page:Int):List<Films>{
        return filmsRepository.searchRun(search,keyword,page).filmsList
    }
    suspend fun getAllPremiers(year: Int, month: String): FilmsList {
        return filmsRepository.getPremiers(year, month)
    }
    suspend fun getListFilmsByListId(listId: List<Int>): List<FilmFull> {
        return List(listId.size) { filmsRepository.getFilmById1(listId[it]).filmRoom }
    }

    suspend fun getAllPopular(page:Int?): FilmsList {
        return filmsRepository.getPopular(page)
    }

    suspend fun getTop250Movies(page:Int?): FilmsList {
        return filmsRepository.getTop250Movies(page)
    }

    suspend fun getFilmById(id: Int): FullInformationFilm {

        val film=filmsRepository.getFilmById1(id)
        val staff=staffRepository.getStaffInFilmId(id)
        val gallery=galleryRepository.getGallery(id)
        val staffActor= mutableListOf <Staff>()
        val staffPersonJob= mutableListOf <Staff>()

        staff.forEach { staff ->
            if (staff.professionKey == "ACTOR") {
                staffActor.add(staff)
            } else {
                staffPersonJob.add(staff)
            }
        }
        return FullInformationFilm(
             film,
            staffActor.toList(),
            staffPersonJob.toList(),
            gallery
         )

    }

    suspend fun getSimilarsById(id: Int): FilmsList {
        return filmsRepository.getSimilarsById(id)
    }
}
