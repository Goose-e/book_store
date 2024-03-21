package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.RolesRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class RolesRefConverter:Converter<Long,RolesRefEnum> {

    override fun convert(rolesId: Long): RolesRefEnum = RolesRefEnum.getEnum(rolesId)
}