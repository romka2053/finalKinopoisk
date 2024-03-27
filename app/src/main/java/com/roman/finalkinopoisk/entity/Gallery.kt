package com.roman.finalkinopoisk.entity

interface Gallery {
    val total:Int
    val items:List<GalleryItems>
}
interface GalleryItems{
    val imageUrl:String?
    val previewUrl:String?
}