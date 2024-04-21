package com.example.book_store.dao

import com.example.book_store.dto.CreatedBookDto
import com.example.book_store.models.Book
import com.example.book_store.repo.BookRepository
import org.springframework.stereotype.Component


@Component
class BookDao(private val bookRepo: BookRepository) {

    fun findByCode(bookCode: String?): Book? = bookRepo.findByCode(bookCode)

    fun save(book: Book): Book = bookRepo.save(book)
    fun findByName(book: String): List<CreatedBookDto?> = bookRepo.findByBook(book)
}