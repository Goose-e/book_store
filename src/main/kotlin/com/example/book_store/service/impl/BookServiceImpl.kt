package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.BIGDECIMAL_ZERO
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.constant.SysConst.INTEGER_ZERO
import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
import com.example.book_store.constant.SysConst.LOCALDATETIME_NULL
import com.example.book_store.dao.BookDao
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dto.*
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
import java.time.LocalDateTime.now

@Service
class BookServiceImpl(
    val bookDao: BookDao,
    val coreEntityDao: CoreEntityDao
) : BookService {

    override fun addBook(bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto> {
        val response: HttpResponseBody<CreatedBookDto> = CreateOrUpdateBookResponse()
        lateinit var modifiedBook: Book
        // add logic
        /*
        * 1. валидирование полей
        * 2. создание объекта книга (в маппере)
        * 3. создание сущности core_entity
        * 4. сохранение в базу двух таблиц в транзакции
        * 5. создание объекта CreatedBookDto - вернуть в конце функции
        *
        * Важно: отсюда обращаешься к уровню ДАО, если нужно что-то сохранить или взять из бд
        * Из дао обращаешься в базу данных
        *
        * Подсказка:
        * Т.к. это и создание, и апдейт, то код будет передаваться
        * То есть, ты ищешь в базе по коду, если он не налл
        * И обновляешь эту сущность
        * Если код налл, то создаешь новую
        *
        * Пока на уникальность в остальном не проверяем, потом надо будет
        * */
        bookRequestDto.bookCode?.let { code ->
            bookDao.findByCode(code)?.let {
                BookMapper.toBook(it, bookRequestDto, code)
                bookDao.save(modifiedBook)
            } ?: run {
                response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book not found"))
            }
        } ?: run {
            val book = Book(
                bookId = generateEntityId(),
                bookName = EMPTY_STRING,
                bookPublisher = EMPTY_STRING,
                bookDescription = EMPTY_STRING,
                bookQuantity = INTEGER_ZERO,
                bookPrice = BIGDECIMAL_ZERO,
                bookPages = INTEGER_ZERO,
                genre = NO_GENRE,
                bookCode = EMPTY_STRING
            )
            modifiedBook = BookMapper.toBook(book, bookRequestDto, GenerationService.generateCode())
             coreEntityDao.findEntityById(book.bookId)?.let {
                 response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Entity already exist"))
             } ?:run{

             CoreEntity(
                 coreEntityId = book.bookId,
                 createDate = now(),
                 deleteDate = LOCALDATETIME_NULL,
                 status  = StatusEnum.BOOK_ACTUAL
             )
             }

//            inTransaction {
//                bookDao.save(modifiedBook)
//                coreEntity.save()
//            }
        }

        response.responseEntity = BookMapper.mapBookToBookDTO(modifiedBook)
        return response
    }

    override fun deleteBook(bookCode: CreatedBookDto): CreatedBookDto {
        TODO("Not yet implemented")
    }

    override fun getBook(bookName: String):HttpResponseBody<CreatedBookDto> {
        val response: HttpResponseBody<CreatedBookDto> = BookFindDTO()
        val bookDTOList: List<CreatedBookDto?> = bookDao.findByName(bookName)
        if (bookDTOList.isEmpty()){
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book not found"))
        }else{
            response.responseEntity = bookDTOList[0]
        }
        return response
    }


    override fun getAllBooks(): List<CreatedBookDto> {
        TODO("Not yet implemented")
    }

    override fun updateBook(bookDTO: CreatedBookDto): CreatedBookDto {
        TODO("Not yet implemented")
    }
}