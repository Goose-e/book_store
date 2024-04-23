package com.example.book_store.models

import com.example.book_store.models.enum.GenreEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "books")
data class Book(
    @Id
    @Column(name = "book_id")
    val bookId: Long? = null,
    @Column(name = "genre_id")
    val genre: GenreEnum,
    @Column(name = "book_publisher")
    val bookPublisher: String,
    @Column(name = "book_price")
    val bookPrice: BigDecimal,
    @Column(name = "book_description")
    val bookDescription: String,
    @Column(name = "book_pages")
    val bookPages: Int,
    @Column(name = "book_quantity")
    val bookQuantity: Int,
    @Column(name = "book_name")
    val bookName: String,
    @Column(name = "book_code")
    val bookCode: String,

)