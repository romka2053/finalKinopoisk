package com.roman.finalkinopoisk.data

import androidx.room.Ignore
import com.roman.finalkinopoisk.entity.FilmsInStaff
import com.roman.finalkinopoisk.entity.Staff
import com.roman.finalkinopoisk.entity.StaffFull

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class StaffDto(
    override val staffId: Int,
    override val nameRu: String?,
    override val nameEn: String?,
    override val posterUrl: String?,
    override val professionKey: String?,
) : Staff(null, staffId, nameRu, nameEn, posterUrl, professionKey)


@JsonClass(generateAdapter = true)
data class StaffFullDto(
    override val personId: Int,
    override val nameRu: String?,
    override val nameEn: String?,
    override val posterUrl: String?,
    override val profession: String?,
    override val films: List<StaffFilmsInStaffDto>
): StaffFull


@JsonClass(generateAdapter = true)
data class StaffFilmsInStaffDto(
    override val nameEn: String?,
    override val filmId: Int,
    override val nameRu: String?,
    override val rating: Float?,
    override val professionKey: String
):FilmsInStaff
