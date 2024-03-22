package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.StatusEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class StatusRefEnumConverter:Converter<StatusEnum,Long> {

        override fun convert(statusesEnum: StatusEnum): Long = statusesEnum.getId()

}