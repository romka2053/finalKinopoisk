package com.roman.finalkinopoisk.data.room

import androidx.room.*
import com.roman.finalkinopoisk.entity.Staff
import com.roman.finalkinopoisk.entity.room.FilmStaff
import com.roman.finalkinopoisk.entity.room.FilmWithStaff
import com.roman.finalkinopoisk.entity.room.FilmsHistory
import com.roman.finalkinopoisk.entity.room.StaffHistory

@Dao
interface StaffDao {
    @Transaction
    @Query("SELECT id FROM films where  id = :id")
   suspend fun getStaffByFilm(id: Int): FilmWithStaff?
    @Transaction
   @Insert(entity = FilmStaff::class)
   suspend fun insertFilmStaff(filmStaff:List<FilmStaff>)

   @Transaction
   @Insert(entity = Staff::class)
   suspend fun insert(staff: List<Staff>):List<Long>

    @Insert(entity = StaffHistory::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaffHistory(staff: StaffHistory)
    @Transaction
    @Query("SELECT * FROM staff_history")
    suspend fun getStaffHistory(): List<StaffHistory>

    @Query("DELETE From staff_history")
    suspend fun deleteAllStaffInHistory()
}