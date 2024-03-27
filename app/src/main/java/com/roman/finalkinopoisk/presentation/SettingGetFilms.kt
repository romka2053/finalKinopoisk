package com.roman.finalkinopoisk.presentation


sealed class SettingGetFilms(val name: String) {
    class PremiersGetSetting(
        name: String,
        val year: Int = 2003,
        val month: String = "JANUARY"
    ) : SettingGetFilms(name)

    class Top250GetSetting(
        name: String,
        val page: Int?=null
    ) : SettingGetFilms(name)

    class TopPopularGetSetting(
        name: String,
        val page: Int?=null
    ) : SettingGetFilms(name)
}
