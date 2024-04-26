package com.example.book_store.map

import com.example.book_store.dto.CreateOrUpdateBookRequestDto
import com.example.book_store.dto.CreatedBookDto
import com.example.book_store.dto.GetBookDto
import com.example.book_store.dto.ListBookDto
import com.example.book_store.models.Book


class BookMapper {


    companion object {
        fun mapBookToBookDTO(book: Book): CreatedBookDto = CreatedBookDto(
        bookName = book.bookName,
            bookPublisher = book.bookPublisher,
            bookDescription = book.bookDescription,
            bookQuantity = book.bookQuantity,
            bookPrice = book.bookPrice,
            bookPages = book.bookPages,
            genre = book.genre,
            bookCode = book.bookCode
        )

        fun toBook(book: Book, bookDto: CreateOrUpdateBookRequestDto, code: String): Book =
                book.copy(

                    bookName = bookDto.bookName,
                    bookPublisher = bookDto.bookPublisher,
                    bookDescription = bookDto.bookDescription,
                    bookQuantity = bookDto.bookQuantity,
                    bookPrice = bookDto.bookPrice,
                    bookPages = bookDto.bookPages,
                    genre = bookDto.genre,
                    bookCode = code
                    )

        fun mapBookToBookListDto(book: List<GetBookDto?>): ListBookDto =  ListBookDto(
            listBookDto = book
        )

        fun mapBookFromListToBookDTO(book: Book?): GetBookDto? = book?.let {
            GetBookDto(
                bookName = it.bookName,
                bookPublisher = book.bookPublisher,
                bookDescription = book.bookDescription,
                bookPrice = book.bookPrice,
                genre = book.genre,

            )
        }

    }

}