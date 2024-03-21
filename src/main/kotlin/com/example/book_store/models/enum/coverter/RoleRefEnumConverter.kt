package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.RoleRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class RoleRefEnumConverter:Converter<RoleRefEnum,Long> {

    override fun convert(rolesEnum: RoleRefEnum): Long = rolesEnum.getId()
}