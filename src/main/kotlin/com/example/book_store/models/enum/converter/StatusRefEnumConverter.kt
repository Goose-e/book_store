package com.example.book_store.models.enum.converter

import com.example.book_store.models.enum.StatusEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class StatusRefEnumConverter : Converter<StatusEnum, Int> {
    override fun convert(statusesEnum: StatusEnum): Int = statusesEnum.getId()
}