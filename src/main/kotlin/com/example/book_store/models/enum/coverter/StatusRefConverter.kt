package com.example.book_store.models.enum.coverter
import com.example.book_store.models.enum.StatusEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class StatusRefConverter:Converter<Long,StatusEnum> {
        override fun convert(statusId: Long): StatusEnum = StatusEnum.getEnum(statusId)
}