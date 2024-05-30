package com.roman.finalkinopoisk.domain

import com.roman.finalkinopoisk.data.FilmsRepository
import com.roman.finalkinopoisk.entity.SeasonsAndSeriesList
import javax.inject.Inject

class GetSeasonsAndSeriesByIdUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository
){
    suspend fun execute(id:Int?): SeasonsAndSeriesList?{
        return if (id!=null)filmsRepository.getSeasonsAndSeries(id)else null
    }
}