package com.example.book_store.models.enum.converter

import com.example.book_store.models.enum.RoleEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class RoleRefEnumConverter : Converter<RoleEnum, Int> {
    override fun convert(rolesEnum: RoleEnum): Int = rolesEnum.getId()
}