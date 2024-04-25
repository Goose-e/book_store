package com.example.book_store.controller

import com.example.book_store.dto.*
import com.example.book_store.service.BookService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1/books")
class CreateBookController(
    private val bookService: BookService
) {
    @PostMapping("/createOrUpdate")
    fun createOrUpdateBook(@Valid @RequestBody bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto> = run{
        bookService.addBook(bookRequestDto)
    }

    @GetMapping("/getByBookName/{bookName}")
    fun getBookByBookName(@PathVariable(value = "bookName")BookRequestDto: GetBookRequestDto):HttpResponseBody<ListBookDto>  {
       val bookFounded = bookService.getBook(BookRequestDto)

        return bookFounded
    }

    @GetMapping("/getByBookName/books")
    fun getAllBook(@PathVariable(value = "bookName")BookRequestDto: GetBookRequestDto):HttpResponseBody<ListBookDto>  {
        val bookFounded = bookService.getBook(BookRequestDto)
        return bookFounded
    }
}