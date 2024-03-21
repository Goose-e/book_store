package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.RolesRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class RolesRefEnumConverter:Converter<RolesRefEnum,Long> {

    override fun convert(rolesEnum: RolesRefEnum): Long = rolesEnum.getId()
}