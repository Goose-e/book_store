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
    @PostMapping("/admin/createOrUpdate")
    fun createOrUpdateBook(@Valid @RequestBody bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto> =
        run {
            bookService.addBook(bookRequestDto)
        }

    @PostMapping("/admin/changeBookStatus")
    fun deleteBook(@Valid @RequestBody bookRequestDto: ChangeBookStatusRequestDto): HttpResponseBody<ChangeBookStatusDto> =
        run {
            bookService.changeBookStatus(bookRequestDto)
        }

    @GetMapping("/getByBookName/{bookName}")
    fun getBookByName(@PathVariable(value = "bookName") bookRequestDto: GetBookRequestDto): HttpResponseBody<ListBookDto> {
        return bookService.getBook(bookRequestDto)
    }
    @GetMapping("/getByBookCode/{bookCode}")
    fun getBookByCode(@PathVariable(value = "bookCode") bookRequestDto: GetBookCodeRequestDto): HttpResponseBody<GetBookCodeDto> {
        return bookService.getBookByCode(bookRequestDto)
    }
    @GetMapping("/home")
    fun getAllBook(): HttpResponseBody<ListBookDto> {
        return bookService.getAllBooks()
    }
}