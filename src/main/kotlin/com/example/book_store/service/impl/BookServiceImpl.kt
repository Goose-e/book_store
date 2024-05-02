package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.BIGDECIMAL_ZERO
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.constant.SysConst.INTEGER_ZERO
import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
import com.example.book_store.constant.SysConst.LOCALDATETIME_NULL
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.BookDao
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.bookDto.*
import com.example.book_store.map.BookMapper
import com.example.book_store.models.Book
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.GenreEnum.NO_GENRE
import com.example.book_store.models.enum.StatusEnum
import com.example.book_store.service.BookService
import com.example.book_store.service.GenerationService
import com.example.book_store.service.GenerationService.Companion.generateEntityId
import org.dbs.validator.ErrorInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime.now

@Service
class BookServiceImpl(
    val bookDao: BookDao,
    val coreEntityDao: CoreEntityDao
) : BookService {

    override fun addBook(bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto> {
        val response: HttpResponseBody<CreatedBookDto> = CreateOrUpdateBookResponse()
        lateinit var modifiedBook: Book
        bookRequestDto.bookCode?.let { code ->
            bookDao.findByCode(code)?.let {
                BookMapper.toBook(it, bookRequestDto, code)
                bookDao.save(modifiedBook)
            } ?: run {
                response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book not found"))
            }
        } ?: run {

            val coreEntity = CoreEntity(
                coreEntityId = generateEntityId(),
                createDate = now(),
                deleteDate = LOCALDATETIME_NULL,
                status = StatusEnum.BOOK_ACTUAL
            )

            val book = Book(
                bookId = coreEntity.coreEntityId,
                bookName = EMPTY_STRING,
                bookPublisher = EMPTY_STRING,
                bookDescription = EMPTY_STRING,
                bookQuantity = INTEGER_ZERO,
                bookPrice = BIGDECIMAL_ZERO,
                bookPages = INTEGER_ZERO,
                genre = NO_GENRE,
                bookCode = EMPTY_STRING,
            )

            modifiedBook = BookMapper.toBook(book, bookRequestDto, GenerationService.generateCode())
            saveInDB(coreEntity, modifiedBook)
        }
        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        response.responseEntity = BookMapper.mapBookToBookDTO(modifiedBook)
        return response
    }


    @Transactional
    fun saveInDB(coreEntity: CoreEntity, book: Book) {
        coreEntityDao.save(coreEntity)
        bookDao.save(book)
    }

    override fun bookOutOfStock(bookCode: DeleteBookRequestDto): HttpResponseBody<DeleteBookDto> {
        val response: HttpResponseBody<DeleteBookDto> = DelBookResponse()
        lateinit var deletedBook: DeleteBookDto
        bookCode.bookCode?.let { code ->
            bookDao.findByCodeForDel(code)?.let { ent ->
                coreEntityDao.save(BookMapper.mapDeleteEntToEnt(ent))
                bookDao.findByCode(code)?.let {
                    deletedBook = BookMapper.mapBookToDelBookDTO(it, ent)
                    response.responseEntity = deletedBook
                    response.message = "Book deleted"

                }

            }
                ?: run {
                    response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book not found"))
                    response.message = "Book not found"
                }
        }
        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }


    @Transactional
    override fun getBook(bookRequestDto: GetBookRequestDto): HttpResponseBody<ListBookDto> {
        val response: HttpResponseBody<ListBookDto> = GetBookResponse()
        lateinit var foundBooks: ListBookDto
        val getBook: MutableCollection<GetBookDtoDB> = bookDao.findByName(bookRequestDto.bookName)
        if (getBook.isNotEmpty()) {
            val listBookDto = getBook.map { BookMapper.mapBookFromListToBookDTO(it) }
            foundBooks = ListBookDto(listBookDto = listBookDto)
            response.responseEntity = foundBooks
            response.message = "Book found"
        } else {
            response.message = "Book not found"
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book not found"))
        }

        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }


    override fun getAllBooks(): HttpResponseBody<ListBookDto> {
        val response: HttpResponseBody<ListBookDto> = GetBookResponse()

        val getBook: MutableCollection<GetBookDtoDB> = bookDao.findAllBooks()
        if (getBook.isNotEmpty()) {
            val listBookDto = getBook.map { BookMapper.mapBookFromListToBookDTO(it) }
            response.responseEntity = ListBookDto(listBookDto = listBookDto)
            response.message = "Books"
        } else {
            response.message = "Books not found"
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Books not found"))
        }

        if (response.errors.isNotEmpty()) {
            response.responseCode = OC_BUGS
        } else {
            response.responseCode = OC_OK
        }
        return response

    }

}