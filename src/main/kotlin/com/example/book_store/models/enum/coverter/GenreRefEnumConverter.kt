package com.example.book_store.models.enum.coverter

import com.example.book_store.models.enum.GenreRefEnum
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class GenreRefEnumConverter:Converter<GenreRefEnum,Long> {

    override fun convert(genreRefEnum: GenreRefEnum): Long = genreRefEnum.getId()
}