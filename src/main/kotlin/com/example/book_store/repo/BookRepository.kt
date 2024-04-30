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
    @Query("select b from Book b where b.bookCode = :code")
    fun findByCode(@Param("code") bookCode: String?): Book?

    @Query("select ent from Book b LEFT JOIN CoreEntity ent on  ent.coreEntityId = b.bookId  where b.bookCode = :code")
    fun findByCodeForDel(@Param("code") bookCode: String?): CoreEntity?

    @Query("select  new com.example.book_store.dto.bookDto.GetBookDtoDB( b.genre, b.bookPublisher, b.bookPrice, b.bookDescription,b.bookName,ent.status)  from Book b LEFT JOIN CoreEntity ent on  ent.coreEntityId = b.bookId where b.bookName = :book  ")
    fun findByBookName(@Param("book") book: String): MutableCollection<GetBookDtoDB>

    @Query("select new com.example.book_store.dto.bookDto.GetBookDtoDB( b.genre, b.bookPublisher, b.bookPrice, b.bookDescription,b.bookName,ent.status ) from Book b LEFT JOIN CoreEntity ent on  ent.coreEntityId = b.bookId")
    fun findAllBooks(): MutableCollection<GetBookDtoDB>

}