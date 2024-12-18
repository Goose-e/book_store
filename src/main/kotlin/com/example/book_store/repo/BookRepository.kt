package com.example.book_store.repo

import com.example.book_store.dto.bookDto.GetBookDtoDB
import com.example.book_store.models.Book
import com.example.book_store.models.CoreEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CrudRepository<Book, Long> {

    @Query("select new com.example.book_store.dto.bookDto.GetBookDtoDB(b.genre, b.bookPublisher, b.bookPrice, b.bookDescription, b.bookName, ent.status, b.image,b.bookPages, b.bookCode,b.bookQuantity) " +
                "from Book b LEFT JOIN CoreEntity ent on ent.coreEntityId = b.bookId " +
                "where b.bookCode = :code")
    fun findByCode(@Param("code") bookCode: String?): GetBookDtoDB?


    @Query("select b from Book b where b.bookCode = :code")
    fun findByCodeForBook(@Param("code") bookCode: String?): Book?

    @Query("select ent from Book b LEFT JOIN CoreEntity ent on  ent.coreEntityId = b.bookId  where b.bookCode = :code")
    fun findByCodeForDel(@Param("code") bookCode: String?): CoreEntity?

    @Query(
        "select new com.example.book_store.dto.bookDto.GetBookDtoDB(b.genre, b.bookPublisher, b.bookPrice, b.bookDescription, b.bookName, ent.status, b.image,b.bookPages, b.bookCode,b.bookQuantity) " +
                "from Book b LEFT JOIN CoreEntity ent on ent.coreEntityId = b.bookId " +
                "where LOWER(b.bookName) LIKE LOWER(CONCAT('%', :book, '%')) and ent.status != 2    "
    )
    fun findByBookName(@Param("book") book: String): MutableCollection<GetBookDtoDB>

    @Query(
        "select new com.example.book_store.dto.bookDto.GetBookDtoDB(b.genre, b.bookPublisher, b.bookPrice, b.bookDescription, b.bookName, ent.status, b.image,b.bookPages, b.bookCode,b.bookQuantity) " +
                "from Book b LEFT JOIN CoreEntity ent on ent.coreEntityId = b.bookId " +
                "where LOWER(b.bookName) LIKE LOWER(CONCAT('%', :book, '%')) and ent.status = 2"
    )
    fun findBlockedBooksByName(@Param("book") book: String): MutableCollection<GetBookDtoDB>

    @Query("select new com.example.book_store.dto.bookDto.GetBookDtoDB( b.genre, b.bookPublisher, b.bookPrice, b.bookDescription,b.bookName,ent.status,b.image,b.bookPages,b.bookCode,b.bookQuantity ) from Book b LEFT JOIN CoreEntity ent on  ent.coreEntityId = b.bookId  where ent.status = 2")
    fun findAllBlockedBooks(): MutableCollection<GetBookDtoDB>

    @Query("select new com.example.book_store.dto.bookDto.GetBookDtoDB( b.genre, b.bookPublisher, b.bookPrice, b.bookDescription,b.bookName,ent.status,b.image,b.bookPages,b.bookCode,b.bookQuantity ) from Book b LEFT JOIN CoreEntity ent on  ent.coreEntityId = b.bookId where ent.status != 2")
    fun findAllBooks(): MutableCollection<GetBookDtoDB>

    @Query("select b.bookName from Book b where b.bookId = :bookId")
    fun findBookNameById(bookId: Long): String

}