package com.example.book_store.service

import com.example.book_store.dto.CreateOrUpdateBookRequestDto
import com.example.book_store.dto.CreatedBookDto
import com.example.book_store.dto.HttpResponseBody

interface BookService {
    fun addBook(bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto>
    fun deleteBook(bookCode: CreatedBookDto): CreatedBookDto
    fun getBook(bookName: String): HttpResponseBody<CreatedBookDto>
    fun getAllBooks(): List<CreatedBookDto>
    fun updateBook(bookDTO: CreatedBookDto): CreatedBookDto
}