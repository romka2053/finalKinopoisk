package com.roman.finalkinopoisk.entity

class Search(
    val typeCinema:String?=null,
    val country:List<Int>?=null,
    val genres:List<Int>?=null,
    val yearFrom:Int?=null,
    val yearTo:Int?=null,
    val ratingFrom:Float?=null,
    val ratingTo:Float?=null,
    val order:String?=null
){

}