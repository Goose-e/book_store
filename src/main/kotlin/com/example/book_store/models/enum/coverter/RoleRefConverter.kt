package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.RoleRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class RoleRefConverter:Converter<Long,RoleRefEnum> {

    override fun convert(rolesId: Long): RoleRefEnum = RoleRefEnum.getEnum(rolesId)
}