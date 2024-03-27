package com.roman.finalkinopoisk.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.roman.finalkinopoisk.entity.FilmFull
import com.roman.finalkinopoisk.entity.room.FilmsWithСollections
import kotlinx.coroutines.flow.Flow


@Dao
interface FilmsDao {
    @Transaction
    @Query("SELECT * FROM films")
    fun getAll(): Flow<List<FilmFull>>

    @Insert(entity = FilmFull::class)
    suspend fun insert(film: FilmFull)



    @Query("SELECT * FROM films where id = :id")
    suspend fun getFilm(id: Int): FilmsWithСollections?

}

