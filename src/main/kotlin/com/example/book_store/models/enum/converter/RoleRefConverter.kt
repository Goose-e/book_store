package com.example.book_store.models.enum.converter

import com.example.book_store.models.enum.RoleEnum
import com.example.book_store.models.enum.RoleEnum.Companion.getEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class RoleRefConverter : Converter<Int, RoleEnum> {
    override fun convert(rolesId: Int): RoleEnum = getEnum(rolesId)
}