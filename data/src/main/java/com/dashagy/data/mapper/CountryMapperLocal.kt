package com.dashagy.data.mapper

import com.dashagy.data.database.entities.RoomCountry
import com.dashagy.domain.entities.Country

class CountryMapperLocal : BaseMapperRepository<RoomCountry, Country> {
    override fun transform(type: RoomCountry): Country =
        Country(
            name = type.name,
            code = type.code,
            flag = type.flag
        )

    override fun transformToRepository(type: Country): RoomCountry =
        RoomCountry(
            id = null,
            name = type.name,
            code = type.code,
            flag = type.flag
        )
}