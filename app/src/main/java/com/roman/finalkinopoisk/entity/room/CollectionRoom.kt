package com.roman.finalkinopoisk.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection")
data class CollectionRoom (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int=0,
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "edit")
    val edit:Boolean=true,
)