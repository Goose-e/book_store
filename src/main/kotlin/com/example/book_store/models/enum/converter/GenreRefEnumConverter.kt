package com.example.book_store.models.enum.converter

import com.example.book_store.models.enum.GenreEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class GenreRefEnumConverter : Converter<GenreEnum, Int> {
    override fun convert(genreEnum: GenreEnum): Int = genreEnum.getId()
}