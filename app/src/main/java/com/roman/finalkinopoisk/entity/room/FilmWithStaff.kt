package com.roman.finalkinopoisk.entity.room

import androidx.room.Junction
import androidx.room.Relation
import com.roman.finalkinopoisk.entity.Staff

data class  FilmWithStaff (
    val id:Int,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            FilmStaff::class,
            parentColumn = "film_id",
            entityColumn = "staff_id"
        )
    )
    val collection:List<Staff>,
        )