package com.roman.finalkinopoisk.entity.room

import android.util.Log
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.roman.finalkinopoisk.entity.Country
import com.roman.finalkinopoisk.entity.Genre

class TypeConvertersFilm {
    @TypeConverter
    fun fromCountryJson(text: String): List<Country>?{
        val arrayTutorialType = object : TypeToken<List<Country>?>() {}.type
        val adapterGson= Gson()
        return adapterGson.fromJson<List<Country>>(text,arrayTutorialType)
    }

    @TypeConverter
    fun dataCountryToJson(listCountry: List<Country>): String {
        val adapterGson= Gson()
        return adapterGson.toJson(listCountry)
    }
    @TypeConverter
    fun fromGenreJson(text: String): List<Genre>?{
        val arrayTutorialType = object : TypeToken<List<Genre>?>() {}.type
        val adapterGson= Gson()
        return adapterGson.fromJson<List<Genre>>(text,arrayTutorialType)
    }

    @TypeConverter
    fun dataGenreToJson(listGenre: List<Genre>): String {
        val adapterGson= Gson()
        return adapterGson.toJson(listGenre)
    }



}
