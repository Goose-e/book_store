package com.example.book_store.models.enum.coverter
import com.example.book_store.models.enum.StatusRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class StatusRefConverter:Converter<Long,StatusRefEnum> {
        override fun convert(statusId: Long): StatusRefEnum = StatusRefEnum.getEnum(statusId)
}