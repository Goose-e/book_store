package com.example.book_store.models.enum.converter

import com.example.book_store.models.enum.GenreEnum
import com.example.book_store.models.enum.GenreEnum.Companion.getEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class GenreRefConverter : Converter<Int, GenreEnum> {

    override fun convert(genreId: Int): GenreEnum = getEnum(genreId)

}