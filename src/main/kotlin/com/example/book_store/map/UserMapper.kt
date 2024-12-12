package com.example.book_store.map

import com.example.book_store.dto.bookDto.*
import com.example.book_store.dto.userDto.GetUserDto
import com.example.book_store.dto.userDto.GetUserDtoDB
import com.example.book_store.models.Book
import com.example.book_store.models.CoreEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {


    companion object {
        fun mapBookToBookDTO(book: Book,bookImage:String): CreatedBookDto = CreatedBookDto(
            bookName = book.bookName,
            bookPublisher = book.bookPublisher,
            bookDescription = book.bookDescription,
            bookQuantity = book.bookQuantity,
            bookPrice = book.bookPrice,
            bookPages = book.bookPages,
            genre = book.genre,
            bookCode = book.bookCode,
            image = bookImage,
        )

        fun mapBookToDelBookDTO(book: Book, ent: CoreEntity): ChangeBookStatusDto = ChangeBookStatusDto(
            bookName = book.bookName,
            bookCode = book.bookCode,
            bookStatusEnum = ent.status
        )


        fun mapUserFromUserListDto(user: GetUserDtoDB): GetUserDto =
            GetUserDto(
                login = user.login,
                password = user.password,
                userAge = user.userAge,
                userRole = user.userRole,
            )

        fun mapDeleteEntToEnt(core: CoreEntity, newStatus: ChangeBookStatusRequestDto): CoreEntity =
            core.copy(
                status = newStatus.bookStatusEnum
            )
    }

}