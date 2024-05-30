package com.roman.finalkinopoisk.presentation

import android.os.Parcelable
import com.roman.finalkinopoisk.entity.Staff
import kotlinx.android.parcel.Parcelize


sealed class SettingGetFilms(open val nameCategory: String, val countItemPage: Int) : Parcelable {
    @Parcelize
    class PremiersGetSetting(
        val name: String,
        val year: Int = 2003,
        val month: String = "JANUARY"

    ) : SettingGetFilms(name, 0)

    @Parcelize
    class Top250GetSetting(
        val name: String,
        val page: Int? = null
    ) : SettingGetFilms(name, 20)

    @Parcelize
    class TopPopularGetSetting(
        val name: String,
        val page: Int? = null
    ) : SettingGetFilms(name, 20)

    @Parcelize
    class FilmsInCollection(val name: String) : SettingGetFilms(name, 0)

    @Parcelize
    class FilmsViewed(val name: String) : SettingGetFilms(name, 0)

    @Parcelize
    class FilmsInStaff(val name: String) : SettingGetFilms(name, 0)

    @Parcelize
    class FilmsSimilar(val name: String, val idFilmParent:Int) : SettingGetFilms(name, 0)

}
