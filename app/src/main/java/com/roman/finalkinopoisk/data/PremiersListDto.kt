package com.roman.finalkinopoisk.data

import android.gesture.GestureOverlayView
import com.roman.finalkinopoisk.entity.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class FilmsListDto (
    @Json override val total:Int,
    @Json(name = "items")override val filmsList: List<FilmsDto>
    ) :FilmsList(total,filmsList)


@JsonClass(generateAdapter = true)
data class FilmsDto(
    override val year: Int?,
    override val posterUrlPreview: String?,
    override val nameRu:String?,
    override val nameEn:String?,
    @Json(name="kinopoiskId")override val id:Int,
    @Json(name = "ratingKinopoisk") override val rating:Float?,
    override val genres: List<GenreDto>,
    override val countries: List<CountryDto>

):Films

@JsonClass(generateAdapter = true)
data class FilmFullDTO(
    override val year: Int?,
    override val posterUrlPreview: String?,
    override val nameRu:String?,
    override val nameEn:String?,
    @Json(name="kinopoiskId")override val id:Int,
    override val posterUrl:String?,
    override val shortDescription:String?,
    override val description:String?,
    @Json(name = "ratingKinopoisk") override val rating:Float?,
    override val genres: List<GenreDto>,
    override val countries: List<CountryDto>
):FilmFull(id, year, posterUrlPreview, nameRu, nameEn, rating, description, shortDescription, posterUrl, genres,countries)

@JsonClass(generateAdapter = true)
data class CountryDto(
    override val country: String?=null
):Country(country)
@JsonClass(generateAdapter = true)
data class GenreDto(
   override val genre: String?=null
):Genre(genre)