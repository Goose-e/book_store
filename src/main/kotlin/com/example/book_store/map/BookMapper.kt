package com.example.book_store.map

import com.example.book_store.dto.bookDto.*
import com.example.book_store.models.Book
import com.example.book_store.models.CoreEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class BookMapper {


    companion object {
        fun mapBookToBookDTO(book: Book,bookImage: String? = null): CreatedBookDto = CreatedBookDto(
            bookName = book.bookName,
            bookPublisher = book.bookPublisher,
            bookDescription = book.bookDescription,
            bookQuantity = book.bookQuantity,
            bookPrice = book.bookPrice,
            bookPages = book.bookPages,
            genre = book.genre,
            bookCode = book.bookCode,
            image =bookImage,
        )

        fun mapBookToDelBookDTO(book: Book, ent: CoreEntity): ChangeBookStatusDto = ChangeBookStatusDto(
            bookName = book.bookName,
            bookCode = book.bookCode,
            bookStatusEnum = ent.status
        )

        fun toBook(book: Book, bookDto: CreateOrUpdateBookRequestDto, code: String, image: ByteArray? = book.image): Book =
            book.copy(
                bookName = bookDto.bookName.lowercase(Locale.getDefault()),
                bookPublisher = bookDto.bookPublisher.lowercase(Locale.getDefault()),
                bookDescription = bookDto.bookDescription,
                bookQuantity = bookDto.bookQuantity,
                bookPrice = bookDto.bookPrice,
                bookPages = bookDto.bookPages,
                genre = bookDto.genre,
                bookCode = code,
                image = image,
            )

        fun mapBookFromListToBookDTO(book: GetBookDtoDB,bookImage:String?): GetBookDto =
            GetBookDto(
                bookName = book.bookName,
                bookPublisher = book.bookPublisher,
                bookDescription = book.bookDescription,
                bookPrice = book.bookPrice,
                genre = book.genre,
                bookStatusEnum = book.bookStatusEnum,
                image = bookImage
            )

        fun mapDeleteEntToEnt(core: CoreEntity, newStatus: ChangeBookStatusRequestDto): CoreEntity =
            core.copy(
                status = newStatus.bookStatusEnum
            )
    }

}