package com.roman.finalkinopoisk.entity

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Json

class CategoryFilms(

    val filmsList: FilmsList,
    val category: String
) {

}

open class FilmsList (
    open val total: Int,
    open val filmsList: List<Films>
)

interface Films {
    val id: Int
    val year: Int?
    val posterUrlPreview: String?
    val nameRu: String?
    val nameEn: String?
    val rating: Float?
    val genres:List<Genre>?
    val countries:List<Country>?
}

@Entity(tableName = "films")
open class FilmFull @JvmOverloads constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    override  val id: Int,
    @ColumnInfo(name = "year")
    override val year: Int?,
    @ColumnInfo(name = "posterUrlPreview")
    override val posterUrlPreview: String?,
    @ColumnInfo(name = "nameRu")
    override val nameRu: String?,
    @ColumnInfo(name = "nameEn")
    override val nameEn: String?,
    @ColumnInfo(name = "rating")
    override val rating: Float?,
    @ColumnInfo(name = "description")
    open val description: String?,
    @ColumnInfo(name = "shortDescription")
    open val shortDescription: String?,
    @ColumnInfo(name = "posterUrl")
    open val posterUrl: String?,

     override val genres: List<Genre>?,
     override val countries:List<Country>?
) : Films


open class Country (
    @Transient  open val country: String?
)

open class Genre ( @Transient open val genre: String?)



