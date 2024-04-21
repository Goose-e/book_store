package com.example.book_store.repo

import com.example.book_store.dto.CreatedBookDto
import com.example.book_store.models.Book
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface BookRepository : CrudRepository<Book, Int> {
    @Query("select b from Book b where b.bookCode = :code")
    fun findByCode(@Param("code") bookCode: String?): Book?

    @Query("select b from Book b where b.bookName = :book")
    fun findByBook(@Param("book") book: String): List<CreatedBookDto?>
}