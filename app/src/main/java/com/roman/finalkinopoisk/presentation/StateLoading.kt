package com.roman.finalkinopoisk.presentation
sealed class StateLoading
{

    object GetInfoFilm:StateLoading()
    object Success:StateLoading()
    data class Error(val massage:String):StateLoading()
}
