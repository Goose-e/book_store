package com.example.book_store.models
import com.example.book_store.models.enum.GenreRefEnum
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.math.BigDecimal


@Table
data class Book(
    @Id
    @Column("book_id")
    val bookId:Long,
    @Column("genre_id")
    val genreId:GenreRefEnum,
    @Column("book_publisher")
    val bookPublisher:String,
    @Column("book_price")
    val bookPrice:BigDecimal,
    @Column("book_description")
    val bookDescription:String,
    @Column("book_pages")
    val bookPages:Int,
    @Column("book_quantity")
    val bookQuantity: Int,
    @Column("book_name")
    val bookName:String,
    @Column("book_code")
    val bookCode:String
)