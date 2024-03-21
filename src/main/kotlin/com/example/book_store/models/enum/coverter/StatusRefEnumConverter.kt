package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.StatusRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class StatusRefEnumConverter:Converter<StatusRefEnum,Long> {

        override fun convert(statusesEnum: StatusRefEnum): Long = statusesEnum.getId()

}