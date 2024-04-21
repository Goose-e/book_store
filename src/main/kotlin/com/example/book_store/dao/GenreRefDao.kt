package com.example.book_store.dao

import com.example.book_store.models.GenreRef
import com.example.book_store.models.enum.GenreEnum.values
import com.example.book_store.repo.GenreRefRepository
import org.springframework.stereotype.Component

@Component
class GenreRefDao(private val genreRefRepo: GenreRefRepository) {
    init {
        values().forEach { genre ->
            genreRefRepo.save(GenreRef(genre, genre.getCode(), genre.getName()))
        }
    }
}