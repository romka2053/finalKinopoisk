package com.roman.finalkinopoisk.data.room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.roman.finalkinopoisk.entity.FilmFull
import com.roman.finalkinopoisk.entity.Staff
import com.roman.finalkinopoisk.entity.room.*

@Database(entities = [
    CollectionRoom::class,
    FilmStaff::class,
    Staff::class,
    FilmFull::class,
    FilmCollection::class,
                     ], version = 1)
@TypeConverters(TypeConvertersFilm::class)
abstract class DataBaseFilms : RoomDatabase()  {
    abstract fun collectionDao():CollectionDao
    abstract fun filmsDao():FilmsDao
    abstract fun staffDao():StaffDao

}
