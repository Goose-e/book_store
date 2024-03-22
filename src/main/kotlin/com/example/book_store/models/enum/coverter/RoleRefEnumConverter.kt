package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.RoleEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class RoleRefEnumConverter:Converter<RoleEnum,Long> {

    override fun convert(rolesEnum: RoleEnum): Long = rolesEnum.getId()
}