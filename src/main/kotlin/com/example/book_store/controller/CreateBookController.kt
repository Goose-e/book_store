package com.example.book_store.controller

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.bookDto.*
import com.example.book_store.service.BookService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1/bookstore")
class CreateBookController(
    private val bookService: BookService
) {
    @PostMapping("/createOrUpdate")
    fun createOrUpdateBook(@Valid @RequestBody bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto> =
        run {
            bookService.addBook(bookRequestDto)
        }

    @PostMapping("/changeBookStatus")
    fun deleteBook(@Valid @RequestBody bookRequestDto: ChangeBookStatusRequestDto): HttpResponseBody<ChangeBookStatusDto> =
        run {
            bookService.changeBookStatus(bookRequestDto)
        }

    @GetMapping("/getByBookName/{bookName}")
    fun getBookByBookName(@PathVariable(value = "bookName") bookRequestDto: GetBookRequestDto): HttpResponseBody<ListBookDto> {
        return bookService.getBook(bookRequestDto)
    }

    @GetMapping("/home")
    fun getAllBook(): HttpResponseBody<ListBookDto> {
        return bookService.getAllBooks()
    }
}