package com.roman.finalkinopoisk.data.room

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.roman.finalkinopoisk.entity.Staff
import com.roman.finalkinopoisk.entity.room.FilmStaff
import com.roman.finalkinopoisk.entity.room.FilmWithStaff
import kotlinx.coroutines.delay

@Dao
interface StaffDao {
    @Transaction
    @Query("SELECT id FROM films where  id = :id")
   suspend fun getStaffByFilm(id: Int): FilmWithStaff?

   @Insert(entity = FilmStaff::class)
   suspend fun insertFilmStaff(filmStaff:List<FilmStaff>)

   @Transaction
   @Insert(entity = Staff::class)
   suspend fun insert(staff: List<Staff>):List<Long>

}