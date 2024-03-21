package com.example.book_store.models.enum.coverter
import com.example.book_store.models.enum.StatusesRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class StatusesRefConverter:Converter<Long,StatusesRefEnum> {
        override fun convert(statusId: Long): StatusesRefEnum = StatusesRefEnum.getEnum(statusId)
}