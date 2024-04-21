package com.example.book_store.models.enum.converter

import com.example.book_store.models.enum.StatusEnum
import com.example.book_store.models.enum.StatusEnum.Companion.getEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class StatusRefConverter : Converter<Int, StatusEnum> {
    override fun convert(statusId: Int): StatusEnum = getEnum(statusId)
}