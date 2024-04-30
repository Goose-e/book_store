package com.example.book_store.service

import com.example.book_store.dto.*

interface BookService {
    fun addBook(bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto>
    fun bookOutOfStock(bookCode: DeleteBookRequestDto): HttpResponseBody<DeleteBookDto>
    fun getBook(bookRequestDto: GetBookRequestDto): HttpResponseBody<ListBookDto>
    fun getAllBooks(): HttpResponseBody<ListBookDto>
}