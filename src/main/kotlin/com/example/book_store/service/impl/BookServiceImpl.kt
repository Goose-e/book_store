package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.BIGDECIMAL_ZERO
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.constant.SysConst.INTEGER_ZERO
import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
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
import com.example.book_store.service.CoreEntityService
import com.example.book_store.service.GenerationService
import org.dbs.validator.ErrorInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BookServiceImpl(
    val bookDao: BookDao,
    val coreEntityDao: CoreEntityDao,
    val coreEntityService: CoreEntityService,
    override val bookMapper: BookMapper
) : BookService {

    override fun addBook(bookRequestDto: CreateOrUpdateBookRequestDto): HttpResponseBody<CreatedBookDto> {
        val response: HttpResponseBody<CreatedBookDto> = CreateOrUpdateBookResponse()
        var modifiedBook: Book? = null

        bookRequestDto.bookCode?.let { code ->
            bookDao.findByCodeForBook(code)?.let {
                modifiedBook =
                    BookMapper.toBook(it, bookRequestDto, code, convertBase64ToByteArray(bookRequestDto.image))
                bookDao.save(modifiedBook!!)
                response.message = "book changed successfully!"
            } ?: run {
                response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book not found"))
                response.message = "book not found"
            }
        } ?: run {
            val validationErrors = validateCreateOrUpdateBookRequestDto(bookRequestDto)
            if (validationErrors.isNotEmpty()) {
                response.errors.addAll(validationErrors)
                response.message = "some field is empty"
            } else {
                val coreEntity = coreEntityService.createCoreEntity(StatusEnum.BOOK_ACTUAL)
                modifiedBook = createBook(coreEntity, bookRequestDto)
                saveInDB(coreEntity, modifiedBook!!)
                response.message = "created book created successfully!"
            }
        }

        response.responseCode = if (response.errors.isEmpty()) OC_OK else OC_BUGS
        response.responseEntity = modifiedBook?.let { BookMapper.mapBookToBookDTO(it, convertImageToBase64(it.image)) }
        return response
    }

    fun validateCreateOrUpdateBookRequestDto(bookRequestDto: CreateOrUpdateBookRequestDto): List<ErrorInfo> {
        val errors = mutableListOf<ErrorInfo>()

        if (bookRequestDto.genre == null) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Genre cannot be null"))
        }
        if (bookRequestDto.bookPublisher.isNullOrBlank()) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book publisher cannot be null or blank"))
        }
        if (bookRequestDto.bookPrice <= BIGDECIMAL_ZERO) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book price cannot be null"))
        }
        if (bookRequestDto.bookDescription.isNullOrBlank()) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book description cannot be null or blank"))
        }
        if (bookRequestDto.bookPages <= 0) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book pages must be a positive number"))
        }
        if (bookRequestDto.bookQuantity <= 0) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book quantity must be a positive number"))
        }
        if (bookRequestDto.bookName.isNullOrBlank()) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book name cannot be null or blank"))
        }

        return errors
    }

    fun createBook(coreEntity: CoreEntity, bookRequestDto: CreateOrUpdateBookRequestDto): Book {
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
            image = null
        )
        return BookMapper.toBook(
            book,
            bookRequestDto,
            GenerationService.generateCode(),
            convertBase64ToByteArray(bookRequestDto.image)
        )
    }

    override fun getAllBlockedBooks(): HttpResponseBody<ListBookDto> {
        var response: HttpResponseBody<ListBookDto> = GetBookResponse()

        val getBook: MutableCollection<GetBookDtoDB> = bookDao.findAllBlockedBooks()
        response = getBookLogic(getBook,response)
        return response
    }

    override fun getBookByCode(bookRequestCodeDto: GetBookCodeRequestDto): HttpResponseBody<GetBookCodeDto> {
        val response: HttpResponseBody<GetBookCodeDto> = GetBookCodeResponse()
        lateinit var foundBooks: GetBookCodeDto
        val getBook: GetBookDtoDB? = bookDao.findByCode(bookRequestCodeDto.bookCode)
        if (getBook != null) {

            val bookDto = BookMapper.mapBookToBookCodeDto(getBook, convertImageToBase64(getBook.image))

            foundBooks = GetBookCodeDto(
                bookName = bookDto.bookName,
                bookCode = bookDto.bookCode,
                bookPublisher = bookDto.bookPublisher,
                bookPrice = bookDto.bookPrice,
                genre = bookDto.genre,
                image = bookDto.image,
                bookDescription = bookDto.bookDescription,
                quantity = bookDto.quantity,
                bookStatusEnum = bookDto.bookStatusEnum,
                bookPages = bookDto.bookPages
            )
            response.responseEntity = foundBooks
            response.message = "Book found"
        } else {
            response.message = "Book not found"
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book not found"))
        }

        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }

    @Transactional
    fun saveInDB(coreEntity: CoreEntity, book: Book) {
        coreEntityDao.save(coreEntity)
        bookDao.save(book)
    }

    fun convertBase64ToByteArray(base64String: String?): ByteArray {
        return base64String?.let {
            val base64Data = it.replace("^data:image/\\w+;base64,".toRegex(), "")
            Base64.getDecoder().decode(base64Data)
        } ?: ByteArray(0)
    }

    fun convertImageToBase64(image: ByteArray?): String? {
        return image?.let { Base64.getEncoder().encodeToString(it) }
    }

    override fun changeBookStatus(bookCode: ChangeBookStatusRequestDto): HttpResponseBody<ChangeBookStatusDto> {
        val response: HttpResponseBody<ChangeBookStatusDto> = DelBookResponse()
        lateinit var deletedBook: ChangeBookStatusDto
        bookCode.bookCode?.let { code ->
            bookDao.findByCodeForDel(code)?.let { ent ->
                val bookStatus = StatusEnum.getEnum(bookCode.bookStatusId)
                coreEntityDao.save(BookMapper.mapDeleteEntToEnt(ent, bookStatus))
                bookDao.findByCodeForBook(code)?.let {
                    deletedBook = BookMapper.mapBookToDelBookDTO(it, ent)
                    response.responseEntity = deletedBook
                    response.message = "Book Status Changed Successfully "
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

            val listBookDto = getBook.map { BookMapper.mapBookFromListToBookDTO(it, convertImageToBase64(it.image)) }
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

    private fun getBookLogic(getBook:MutableCollection<GetBookDtoDB>,response:HttpResponseBody<ListBookDto>):HttpResponseBody<ListBookDto>{
        if (getBook.isNotEmpty()) {
            val listBookDto = getBook.map { BookMapper.mapBookFromListToBookDTO(it, convertImageToBase64(it.image)) }
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

    override fun getAllBooks(): HttpResponseBody<ListBookDto> {
        var response: HttpResponseBody<ListBookDto> = GetBookResponse()

        val getBook: MutableCollection<GetBookDtoDB> = bookDao.findAllBooks()
        response = getBookLogic(getBook,response)
        return response

    }

}