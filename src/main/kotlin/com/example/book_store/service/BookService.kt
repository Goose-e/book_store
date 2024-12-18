package com.example.book_store.service

import com.example.book_store.dao.BookDao
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.bookDto.*
import com.example.book_store.map.BookMapper

interface BookService {
    fun addBook(bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto> 
    fun changeBookStatus(bookCode: ChangeBookStatusRequestDto): HttpResponseBody<ChangeBookStatusDto>
    fun getBook(bookRequestDto: GetBookRequestDto): HttpResponseBody<ListBookDto>
    fun getAllBooks(): HttpResponseBody<ListBookDto>
    fun getAllBlockedBooks(): HttpResponseBody<ListBookDto>
    fun getBookByCode(bookRequestCodeDto: GetBookCodeRequestDto):HttpResponseBody<GetBookCodeDto>
    val bookMapper: BookMapper
}