package com.example.book_store.dao

import com.example.book_store.dto.bookDto.GetBookDtoDB
import com.example.book_store.models.Book
import com.example.book_store.models.CoreEntity
import com.example.book_store.repo.BookRepository
import org.springframework.stereotype.Component


@Component
class BookDao(private val bookRepo: BookRepository) {

    fun findByCode(bookCode: String?): Book? = bookRepo.findByCode(bookCode)
    fun findAllBooks(): MutableCollection<GetBookDtoDB> =bookRepo.findAllBooks()
    fun save(book: Book): Book = bookRepo.save(book)
    fun findByCodeForDel(bookCode: String?): CoreEntity? = bookRepo.findByCodeForDel(bookCode)
    fun findByName(book: String): MutableCollection<GetBookDtoDB> = bookRepo.findByBookName(book)
}