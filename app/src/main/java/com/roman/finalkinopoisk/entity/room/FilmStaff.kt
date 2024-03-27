package com.roman.finalkinopoisk.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "film_staff",
    primaryKeys = ["film_id","staff_id"]
)
data class FilmStaff (
    @ColumnInfo(name = "film_id")
    val film_id:Int,
    @ColumnInfo(name = "staff_id")
    val staff_id:Int,
)