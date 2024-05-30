package com.roman.finalkinopoisk.data

import android.util.Log
import com.roman.finalkinopoisk.data.room.CollectionDao
import com.roman.finalkinopoisk.data.room.FilmsDao
import com.roman.finalkinopoisk.entity.*
import com.roman.finalkinopoisk.entity.room.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsRepository @Inject constructor(
    private val premiersDataSource: PremiersDataSource,
    private val filmsDao: FilmsDao,
    private val collectionDao: CollectionDao

) {




    suspend fun getSeasonsAndSeries(id:Int):SeasonsAndSeriesList{
        return premiersDataSource.seasonsAndSeries.getSeasonsByFilmId(id)
    }

   private suspend fun addFilmInRoom(film: FilmFull) {
        filmsDao.insert(film)

    }
    suspend fun updateViewFilm(id: Int){
        filmsDao.updateViewFilm(id)
    }
    suspend fun deleteAllFilmsInHistory()
    {
        filmsDao.deleteAllFilmsInHistory()
    }
    private suspend fun getFilmByRoom(id: Int): FilmsWithСollections? {
        return filmsDao.getFilm(id)
    }
    suspend fun getIsViewFilm(id:Int):Boolean{
        return filmsDao.isViewFilm(id)
    }

    fun getAllFilmsFromRoom(): Flow<List<FilmFull>> {
        return filmsDao.getAll()
    }

    suspend fun addCollectionToRoom(collection: CollectionRoom){
        if (collection.name_collection=="") throw Exception("Null")
        return collectionDao.insert(collection)
    }

    suspend fun addFilmCollection(filmCollection: FilmCollection) {
        collectionDao.insertFilmCollection(filmCollection)
    }

    suspend fun deleteFilmCollection(filmCollection: FilmCollection) {
        collectionDao.deleteFilmCollection(filmCollection)
    }
    suspend fun deleteAllFilmCollectionWhereCollectionId(nameCollection: String){
        collectionDao.deleteAllFilmCollectionWhereCollectionId(nameCollection)
    }
    suspend fun deleteCollectionWhereCollectionName(nameCollection: String){
        collectionDao.deleteCollectionWhereCollectionName(nameCollection)
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

    suspend fun getGenresAndCounties(): GenreAndCountryList {
        return premiersDataSource.genresAndCounties.getGenresAndCountries()
    }

    suspend fun getFilmsIdViewed(): Set<Int> {
        return filmsDao.getFilmsIdViewed().toSet()
    }
    suspend fun insertFilmHistory(film:FilmsHistory){
        filmsDao.insertFilmHistory(film)
    }
    suspend fun getFilmsHistory():List<FilmsHistory>{
        return filmsDao.getFilmHistory()
    }
    suspend fun getFilmsViewed():List<FilmFull>
    {
        return filmsDao.getFilmsViewed()
    }



    suspend fun getFilmById1(id: Int): FilmsWithСollections {

        var filmRoom = getFilmByRoom(id)

        if (filmRoom==null)
        {
            filmRoom = FilmsWithСollections(
                premiersDataSource.filmById.getFilmWhereId(id) as FilmFull,
                emptyList()
            )
            addFilmInRoom(filmRoom.filmRoom)
        }


        return filmRoom
    }

     fun hhh(id:Int):Flow<FilmsWithСollections?>{
        return filmsDao.getFilmFlow(id)
    }

    suspend fun getSimilarById(id: Int): FilmsList {Log.d("1111","qqqqqqqqqqqqqqqqqqqqqq")
        return premiersDataSource.similar.getSimilarsWhereFilmId(id)
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
    suspend fun getFilmsInCollection(name:String):CollectionsWithFilms
    {
        return collectionDao.getFilmsWhereCollection(name)
    }
    suspend fun getCollectionInFilm(nameCollection:String,idFilm:Int):Boolean
    {
        return collectionDao.getCollectionInFilm(nameCollection,idFilm)
    }
    suspend fun getFilmsCountInCollection(name:String):Int
    {
        return collectionDao.getFilmsCountWhereCollection(name)?:0
    }
    private companion object{
        private const val maxCountFilmsHistory=20
        private const val maxCountStaffHistory=20
    }
}