package com.roman.finalkinopoisk.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "film_collection",
    primaryKeys = ["film_id","name_collection"]
)
data class FilmCollection (
    @ColumnInfo(name = "film_id")
    val film_id:Int,
    @ColumnInfo(name = "name_collection")
    val name_collection:String,

    )