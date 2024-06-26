package com.roman.finalkinopoisk.data

import com.roman.finalkinopoisk.entity.Gallery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryRepository @Inject constructor (
    private val galleryDataSource: PremiersDataSource
) {
    suspend fun getGallery(idFilm:Int):Gallery
    {
        return galleryDataSource.gallery.getGalleryWhereFilmId(idFilm)
    }
}