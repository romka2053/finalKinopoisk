package com.roman.finalkinopoisk.presentation

import com.roman.finalkinopoisk.entity.CategoryFilms

sealed class GetFilms() {
data class Popular(val ba:CategoryFilms):GetFilms()
}