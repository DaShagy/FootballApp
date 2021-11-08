package com.dashagy.data.service.services

import android.content.Context
import com.dashagy.data.mapper.CountryMapperService
import com.dashagy.data.service.RequestGenerator
import com.dashagy.data.service.api.ApiSportsFootball
import com.dashagy.domain.entities.Country
import com.dashagy.domain.util.ResultWrapper

class CountryService(context: Context) {
    private val api: RequestGenerator = RequestGenerator(context)
    private val mapper: CountryMapperService = CountryMapperService()

    fun getAllCountries() : ResultWrapper<List<Country>>{
        val service = api.createService(ApiSportsFootball.Countries::class.java)
            .getAllCountries()
        val response = service.execute()
        if (response.isSuccessful){
            return ResultWrapper.Success(
                response.body()?.response?.map {
                    mapper.transform(it)
                }!!
            )
        }
        return ResultWrapper.Error(Exception(response.message()))
    }
}
