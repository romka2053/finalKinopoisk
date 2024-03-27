package com.roman.finalkinopoisk.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "film_collection",
    primaryKeys = ["film_id","collection_id"]
)
data class FilmCollection (
    @ColumnInfo(name = "film_id")
    val film_id:Int,
    @ColumnInfo(name = "collection_id")
    val collection_id:Int,

    )