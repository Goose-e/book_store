package com.example.book_store.map

import com.example.book_store.dto.bookDto.*
import com.example.book_store.models.Book
import com.example.book_store.models.CoreEntity


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
        fun mapBookToDelBookDTO(book: Book, ent :CoreEntity): ChangeBookStatusDto = ChangeBookStatusDto(
            bookName = book.bookName,
            bookCode = book.bookCode,
            bookStatusEnum = ent.status
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

        fun mapBookFromListToBookDTO(book: GetBookDtoDB): GetBookDto =
            GetBookDto(
                bookName = book.bookName,
                bookPublisher = book.bookPublisher,
                bookDescription = book.bookDescription,
                bookPrice = book.bookPrice,
                genre = book.genre,
                bookStatusEnum = book.bookStatusEnum,
                )

        fun mapDeleteEntToEnt(core: CoreEntity,newStatus:ChangeBookStatusRequestDto): CoreEntity =
            core.copy(
                status = newStatus.bookStatusEnum
            )
    }

}