package com.roman.finalkinopoisk.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.roman.finalkinopoisk.data.StaffFilmsInStaffDto
import com.roman.finalkinopoisk.presentation.ProfessionsKey
import java.util.*

@Entity(tableName = "staff")
open class Staff(
    @PrimaryKey(true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "staffId")
    open val staffId: Int,
    @ColumnInfo(name = "nameRu")
    open val nameRu: String?,
    @ColumnInfo(name = "nameEn")
    open val nameEn: String?,
    @ColumnInfo(name = "posterUrl")
    open val posterUrl: String?,
    @ColumnInfo(name = "professionKey")
    open val professionKey: String?,
    @ColumnInfo(name="dateSave")
    open val dateSave:Long= Calendar.getInstance().timeInMillis,
    )

interface StaffFull {
    val personId: Int
    val nameRu: String?
    val nameEn: String?
    val posterUrl: String?
    val profession: String?
    val films: List<FilmsInStaff>

}
class FilmographyStaff(
    val nameRu: String?,
    val mapKeyProfessionFilm: Map<ProfessionsKey, List<FilmsInStaff>>
)

interface FilmsInStaff {
    val filmId: Int
    val nameRu: String?
    val nameEn: String?
    val rating: Float?
    val professionKey: String
}

data class StaffWithFilms(
     val staff: StaffFull,
    val filmsPopular: FilmsList,

) {

}