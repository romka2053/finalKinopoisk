package com.roman.finalkinopoisk.data


import com.roman.finalkinopoisk.data.room.StaffDao
import com.roman.finalkinopoisk.entity.Staff
import com.roman.finalkinopoisk.entity.StaffFull
import com.roman.finalkinopoisk.entity.room.FilmStaff
import com.roman.finalkinopoisk.entity.room.FilmWithStaff
import com.roman.finalkinopoisk.entity.room.StaffHistory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StaffRepository @Inject constructor(
    private val premiersDataSource: PremiersDataSource,
    private val staffDao: StaffDao
){
    private var staff:FilmWithStaff?=null

    suspend fun getStaffById(id:Int):StaffFull{
       return premiersDataSource.staffById.staffById(id)
    }

    private suspend fun addRoomStaff(film:FilmWithStaff)
    {
        val idNewStaff=staffDao.insert(film.collection)
        val staffFilms= List(idNewStaff.size){FilmStaff(film.id,idNewStaff[it].toInt())}
        staffDao.insertFilmStaff(staffFilms)
    }
    suspend fun insertStaffHistory(staff:StaffHistory){
        staffDao.insertStaffHistory(staff)
    }
    suspend fun getStaffHistory():List<StaffHistory>{
        return staffDao.getStaffHistory()
    }
    suspend fun deleteAllStaffInHistory()
    {
        staffDao.deleteAllStaffInHistory()
    }
    suspend fun getStaffInFilmId(idFilm:Int):List<Staff>
    {

        staffDao.getStaffByFilm(idFilm)?.let {
               staff=it
            if (it.collection.isNotEmpty()) return it.collection
        }
        val listStaff=premiersDataSource.staffByFilmId.staffByIdFilms(idFilm) as List<Staff>
        staff= FilmWithStaff(idFilm,listStaff)
        addRoomStaff(staff!!)
        return listStaff
    }

}