package com.example.book_store.dao

import com.example.book_store.dto.GetBookDto
import com.example.book_store.models.Book
import com.example.book_store.repo.BookRepository
import org.springframework.stereotype.Component


@Component
class BookDao(private val bookRepo: BookRepository) {

    fun findByCode(bookCode: String?): Book? = bookRepo.findByCode(bookCode)
    fun findAllBooks(): List<Book?> = bookRepo.findAllBooks()
    fun save(book: Book): Book = bookRepo.save(book)

    fun findByName(book: String): MutableCollection<Book> = bookRepo.findByBookName(book)
}