package com.roman.finalkinopoisk.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.roman.finalkinopoisk.R

@Entity(tableName = "collection")
data class CollectionRoom (
    @PrimaryKey
    @ColumnInfo(name = "name_collection")
    val name_collection:String,
    @ColumnInfo(name = "edit")
    val edit:Boolean=true,
    @ColumnInfo(name="image")
    val image:Int= R.drawable.icon_profile,
    @ColumnInfo(name="image_active")
    val image_active:Int= 0,
    private var _count:Int=0
){

    val count get() = _count
    fun setCount(count:Int)
    {
        _count=count
    }
}