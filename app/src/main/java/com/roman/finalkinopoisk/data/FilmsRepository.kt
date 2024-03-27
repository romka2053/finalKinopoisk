package com.roman.finalkinopoisk.data

import android.util.Log
import com.roman.finalkinopoisk.data.room.CollectionDao
import com.roman.finalkinopoisk.data.room.FilmsDao
import com.roman.finalkinopoisk.entity.*
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import com.roman.finalkinopoisk.entity.room.FilmCollection
import com.roman.finalkinopoisk.entity.room.FilmsWithСollections
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val premiersDataSource: PremiersDataSource,
    private val filmsDao: FilmsDao,
    private val collectionDao: CollectionDao

) {

    private var film: FilmsWithСollections? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)



    suspend fun addFilmInRoom(film: FilmFull) {


            filmsDao.insert(film)


    }

    suspend fun getFilmByRoom(id: Int): FilmsWithСollections? {
        return filmsDao.getFilm(id)
    }

    fun getAllFilmsFromRoom(): Flow<List<FilmFull>> {
        return filmsDao.getAll()
    }

    suspend fun addCollectionToRoom(collection: CollectionRoom) :Long{
        return collectionDao.insert(collection)
    }
    suspend fun addFilmCollection(filmCollection: FilmCollection){
        collectionDao.insertFilmCollection(filmCollection)
    }

    suspend fun deleteFilmCollection(filmCollection: FilmCollection){
        collectionDao.deleteFilmCollection(filmCollection)
    }

   suspend fun getAllCollection(): List<CollectionRoom> {
        return collectionDao.getAll()
    }

    suspend fun getPremiers(year: Int, month: String): FilmsList {
        return premiersDataSource.premiers.getPremiersWhereYeaMonth(year, month)
    }

    suspend fun getPopular(page: Int?): FilmsList {
        return premiersDataSource.popular.getPopularWherePage(page)
    }

    suspend fun getTop250Movies(page: Int?): FilmsList {
        return premiersDataSource.top250.getTop250WherePage(page)
    }



    //переносить во viewModel
    suspend fun getFilmById1(id: Int): FilmsWithСollections {
        val filmRoom = getFilmByRoom(id)
        filmRoom?.let {
            Log.d("1111","room")
            film=it
            return it
        }
        film=FilmsWithСollections(
            premiersDataSource.filmById.getFilmWhereId(id) as FilmFull,
            emptyList()
        )

            addFilmInRoom(film!!.filmRoom)
Log.d("1234","films")
        return film!!
    }

    suspend fun getSimilarsById(id: Int): FilmsList {
        return premiersDataSource.similars.getSimilarsWhereFilmId(id)
    }


    suspend fun searchRun(search: Search, keyword: String, page: Int): FilmsList {
        return premiersDataSource.search.search(
            type = search.typeCinema,
            countries = search.country,
            genres = search.genres,
            yearFrom = search.yearFrom,
            yearTo = search.yearTo,
            ratingFrom = search.ratingFrom,
            ratingTo = search.ratingTo,
            page = page,
            order = search.order,
            keyword = keyword
        )
    }
}