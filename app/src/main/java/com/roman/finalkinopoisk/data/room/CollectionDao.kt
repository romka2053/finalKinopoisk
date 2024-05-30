package com.roman.finalkinopoisk.data.room

import androidx.room.*
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import com.roman.finalkinopoisk.entity.room.CollectionsWithFilms
import com.roman.finalkinopoisk.entity.room.FilmCollection
import com.roman.finalkinopoisk.entity.room.FilmsWithСollections
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.Async.Execute

@Dao
interface CollectionDao {

    @Transaction
    @Query("SELECT * FROM collection")
    suspend fun getAll(): List<CollectionRoom>

    @Insert(entity = CollectionRoom::class)
    suspend fun insert(collection: CollectionRoom)

    @Query("SELECT * FROM collection where name_collection=:name")
    suspend fun getFilmsWhereCollection(name:String): CollectionsWithFilms

    @Query("select EXISTS (Select * from film_collection where film_id=:idFilm and name_collection=:nameCollection Limit 1)")
    suspend fun getCollectionInFilm(nameCollection:String,idFilm:Int): Boolean
    @Query("SELECT SUM(1) FROM film_collection where name_collection=:name")
    suspend fun getFilmsCountWhereCollection(name:String): Int?

    @Insert(entity = FilmCollection::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilmCollection(FilmCollection:FilmCollection)

    @Delete(entity = FilmCollection::class)
    suspend fun deleteFilmCollection(FilmCollection:FilmCollection)
    @Query("DELETE from film_collection where name_collection=:nameCollection ")
    suspend fun deleteAllFilmCollectionWhereCollectionId(nameCollection: String)
    @Query("DELETE from collection where name_collection=:nameCollection ")
    suspend fun deleteCollectionWhereCollectionName(nameCollection: String)
}
