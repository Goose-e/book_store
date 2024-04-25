package com.example.book_store.repo

import com.example.book_store.models.Book
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CrudRepository<Book, Int> {
    @Query("select b from Book b where b.bookCode = :code")
    fun findByCode(@Param("code") bookCode: String?): Book?

    @Query("select b.bookName,b.bookPrice,b.bookPublisher from Book b where b.bookName = :book")
    fun findByBookName(@Param("book") book: String): MutableCollection<Book>

    @Query("select b.bookName,b.bookDescription,b.bookPrice,b.bookPublisher from Book b group by b.bookName")
    fun findAllBooks(): List<Book?>

}