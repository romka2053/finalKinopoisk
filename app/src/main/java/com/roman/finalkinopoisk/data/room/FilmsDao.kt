package com.roman.finalkinopoisk.data.room

import androidx.room.*
import com.roman.finalkinopoisk.entity.FilmFull
import com.roman.finalkinopoisk.entity.room.FilmsHistory
import com.roman.finalkinopoisk.entity.room.FilmsWithСollections
import kotlinx.coroutines.flow.Flow


@Dao
interface FilmsDao {
    @Transaction
    @Query("SELECT * FROM films")
    fun getAll(): Flow<List<FilmFull>>

    @Insert(entity = FilmFull::class)
    suspend fun insert(film: FilmFull)
    @Query("UPDATE films SET filmViewed=NOT filmViewed WHERE id=:id")
    suspend fun updateViewFilm(id: Int)

    @Query("SELECT * FROM films where id = :id")
    suspend fun getFilm(id: Int): FilmsWithСollections?

    @Query("SELECT * FROM films where id = :id")
     fun getFilmFlow(id: Int): Flow<FilmsWithСollections?>

    @Query("SELECT filmViewed FROM films where id = :id")
    suspend fun isViewFilm(id: Int): Boolean
    @Transaction
    @Query("SELECT id FROM films where filmViewed=true")
    suspend fun getFilmsIdViewed(): List<Int>
    @Transaction
    @Query("SELECT * FROM film_history")
    suspend fun getFilmHistory(): List<FilmsHistory>
    @Transaction
    @Query("SELECT * FROM films where filmViewed=1")
    suspend fun getFilmsViewed(): List<FilmFull>
    @Insert(entity =FilmsHistory::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilmHistory(film:FilmsHistory)

    @Query("DELETE From film_history")
    suspend fun deleteAllFilmsInHistory()



}

