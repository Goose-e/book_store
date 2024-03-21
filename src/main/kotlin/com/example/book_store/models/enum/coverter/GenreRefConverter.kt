package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.GenreRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class GenreRefConverter:Converter<Long,GenreRefEnum> {

    override fun convert(genreId: Long):GenreRefEnum =GenreRefEnum.getEnum(genreId)

}