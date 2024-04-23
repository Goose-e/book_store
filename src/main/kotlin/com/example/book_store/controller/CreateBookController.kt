package com.example.book_store.controller

import com.example.book_store.dto.CreateOrUpdateBookRequestDto
import com.example.book_store.dto.CreatedBookDto
import com.example.book_store.dto.HttpResponseBody
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
    fun getBookByBookName(@PathVariable(value = "bookName") bookName: String):HttpResponseBody<CreatedBookDto>  {
       val bookFinded = bookService.getBook(bookName = bookName)
        return bookFinded
    }
}