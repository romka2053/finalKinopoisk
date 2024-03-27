package com.roman.finalkinopoisk.data.room

import androidx.room.*
import com.roman.finalkinopoisk.entity.room.CollectionRoom
import com.roman.finalkinopoisk.entity.room.FilmCollection
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Transaction
    @Query("SELECT * FROM collection")
    suspend fun getAll(): List<CollectionRoom>

    @Insert(entity = CollectionRoom::class)
    suspend fun insert(collection: CollectionRoom):Long


    @Insert(entity = FilmCollection::class)
    suspend fun insertFilmCollection(FilmCollection:FilmCollection)

    @Delete(entity = FilmCollection::class)
    suspend fun deleteFilmCollection(FilmCollection:FilmCollection)
}
