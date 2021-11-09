package com.dashagy.data.mapper

import com.dashagy.data.service.responses.CountryResponse
import com.dashagy.domain.entities.Country

class CountryMapperService : BaseMapperRepository<CountryResponse, Country> {
    override fun transform(type: CountryResponse): Country =
        Country(
            name = type.name,
            code = type.code,
            flag = type.flag
        )

    override fun transformToRepository(type: Country): CountryResponse =
        CountryResponse(
            name = type.name,
            code = type.code,
            flag = type.flag
        )

}
