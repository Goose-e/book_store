package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.StatusesRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class StatusesRefEnumConverter:Converter<StatusesRefEnum,Long> {

        override fun convert(statusesEnum: StatusesRefEnum): Long = statusesEnum.getId()

}