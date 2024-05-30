package com.roman.finalkinopoisk.presentation
sealed class StateLoading
{

    object Loading:StateLoading()
    object Success:StateLoading()
    data class Error(val massage:String):StateLoading()
}
