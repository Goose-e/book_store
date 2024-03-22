package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.GenreEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class GenreRefConverter:Converter<Long,GenreEnum> {

    override fun convert(genreId: Long):GenreEnum =GenreEnum.getEnum(genreId)

}