package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.RoleEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class RoleRefConverter:Converter<Long,RoleEnum> {

    override fun convert(rolesId: Long): RoleEnum = RoleEnum.getEnum(rolesId)
}