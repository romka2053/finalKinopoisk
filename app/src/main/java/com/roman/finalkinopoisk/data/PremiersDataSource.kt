package com.roman.finalkinopoisk.data

import com.roman.finalkinopoisk.data.room.GenresAndCountriesDTO
import com.roman.finalkinopoisk.entity.Films
import com.roman.finalkinopoisk.entity.Staff
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.*
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
@Singleton
class PremiersDataSource  @Inject constructor(){
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val premiers: PremiersGet = retrofit.create(PremiersGet::class.java)
    val popular:PopularGet=retrofit.create(PopularGet::class.java)
    val top250:Top250MoviesGet=retrofit.create(Top250MoviesGet::class.java)
    val filmById:FilmByIdGet=retrofit.create(FilmByIdGet::class.java)
    val staffByFilmId:StaffByIdFilms=retrofit.create(StaffByIdFilms::class.java)
    val gallery:GalleryGet=retrofit.create(GalleryGet::class.java)
    val similar:SimilarGet=retrofit.create(SimilarGet::class.java)
    val search:SearchGet=retrofit.create(SearchGet::class.java)
    val staffById:StaffById=retrofit.create(StaffById::class.java)
    val genresAndCounties:GenresAndCountriesGet=retrofit.create(GenresAndCountriesGet::class.java)
    val seasonsAndSeries:SeasonsAndSeriesGet=retrofit.create(SeasonsAndSeriesGet::class.java)

    private companion object {
        private const val API_KEY2= "4da99d7d-5320-40d1-bf0b-e7e2d92c920a"
        private const val API_KEY3="e2ec793d-8d7b-4a36-bd06-5a3962d8dbb5"
        private const val API_KEY="109757dd-5abd-4407-8678-31430b47a430"
    }


    interface PopularGet{
        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/collections?&=&type=TOP_POPULAR_ALL")
        suspend fun getPopularWherePage(@Query("page")page:Int?):FilmsListDto
    }
    interface SeasonsAndSeriesGet{
        @Headers("X-API-KEY: $API_KEY")
        @GET("api/v2.2/films/{id}/seasons")
        suspend fun getSeasonsByFilmId(@Path("id")id:Int):SeasonsAndSeriesListDto
    }
    interface GenresAndCountriesGet{
        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/filters")
        suspend fun getGenresAndCountries():GenresAndCountriesDTO
    }

    interface Top250MoviesGet{
        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/collections?&=&type=TOP_250_MOVIES")
        suspend fun getTop250WherePage(@Query("page")page:Int?):FilmsListDto
    }
    interface FilmByIdGet{
        @Headers("X-API-KEY: $API_KEY")
        @GET("api/v2.2/films/{id}")
        suspend fun getFilmWhereId(@Path("id")id:Int):FilmFullDTO
    }
    interface StaffByIdFilms{
        @Headers("X-API-KEY: $API_KEY")
        @GET("api/v1/staff")
        suspend fun staffByIdFilms(@Query("filmId")filmId:Int):List<StaffDto>
    }
    interface StaffById{
        @Headers("X-API-KEY: $API_KEY")
        @GET("api/v1/staff/{id}")
        suspend fun staffById(@Path("id")id:Int):StaffFullDto
    }

    interface PremiersGet {
        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/premieres")
        suspend fun getPremiersWhereYeaMonth(@Query("year") year: Int,@Query("month") month: String): FilmsListDto
    }
    interface GalleryGet{
        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/{idFilm}/images")
        suspend fun getGalleryWhereFilmId(@Path ("idFilm")idFilm:Int):GalleryListDto
    }
    interface SimilarGet{
        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films/{idFilm}/similars")
        suspend fun getSimilarsWhereFilmId(@Path ("idFilm")idFilm:Int):FilmsSimilarListDto

    }
    interface SearchGet{
        @Headers("X-API-KEY: $API_KEY")
        @GET("/api/v2.2/films")
        suspend fun search(
            @Query("countries") countries: List<Int>?,
            @Query("genres")genres: List<Int>?,
            @Query("order")order: String?,
            @Query("type")type: String?,
            @Query("ratingFrom")ratingFrom: Float?,
            @Query("ratingTo")ratingTo: Float?,
            @Query("yearFrom")yearFrom: Int?,
            @Query("yearTo")yearTo: Int?,
            @Query("keyword")keyword: String?,
            @Query("page")page:Int?
        ):FilmsListDto
    }
}