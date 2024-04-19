package com.example.book_store.service

import com.example.book_store.dto.BookDTO
import com.example.book_store.dto.CreateOrUpdateBookRequestDto
import com.example.book_store.dto.CreatedBookDto

interface BookService {
    fun addBook(bookRequestDto: CreateOrUpdateBookRequestDto): CreatedBookDto
    fun deleteBook(bookCode: BookDTO): BookDTO
    fun getBook(bookName: BookDTO): BookDTO
    fun getAllBooks(): List<BookDTO>
    fun updateBook(bookDTO: BookDTO): BookDTO
}