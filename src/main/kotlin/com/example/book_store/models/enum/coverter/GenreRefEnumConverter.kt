package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.GenreEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class GenreRefEnumConverter:Converter<GenreEnum,Long> {

    override fun convert(genreEnum: GenreEnum): Long = genreEnum.getId()
}