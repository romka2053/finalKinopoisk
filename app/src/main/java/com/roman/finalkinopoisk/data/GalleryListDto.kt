package com.roman.finalkinopoisk.data

import com.roman.finalkinopoisk.entity.Gallery
import com.roman.finalkinopoisk.entity.GalleryItems
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryListDto(
    override val total: Int,
    override val items: List<GalleryItemsDto>
):Gallery
@JsonClass(generateAdapter = true)
data class GalleryItemsDto(
    override val imageUrl: String?,
    override val previewUrl: String?
):GalleryItems