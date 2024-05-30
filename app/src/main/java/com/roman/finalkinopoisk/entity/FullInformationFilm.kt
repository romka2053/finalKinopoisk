package com.roman.finalkinopoisk.entity

import com.roman.finalkinopoisk.entity.room.FilmsWithСollections

data class FullInformationFilm(
    val film: FilmsWithСollections,
    val staffListActor: List<Staff>,
    val staffListPersonJob: List<Staff>,
    val galleryList: Gallery
)