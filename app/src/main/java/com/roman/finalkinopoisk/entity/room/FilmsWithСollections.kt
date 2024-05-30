package com.roman.finalkinopoisk.entity.room

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.roman.finalkinopoisk.entity.FilmFull

data class FilmsWithСollections (
    @Embedded
       val filmRoom:FilmFull,
    @Relation(
        parentColumn = "id",
        entityColumn = "name_collection",
        associateBy = Junction(
            FilmCollection::class,
            parentColumn = "film_id",
            entityColumn = "name_collection"
        )
    )
    val collection:List<CollectionRoom>


    )
