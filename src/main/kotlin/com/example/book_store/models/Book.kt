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
    @Column(name = "image")
    val image: ByteArray?  = null,
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (bookId != other.bookId) return false
        if (genre != other.genre) return false
        if (bookPublisher != other.bookPublisher) return false
        if (bookPrice != other.bookPrice) return false
        if (bookDescription != other.bookDescription) return false
        if (bookPages != other.bookPages) return false
        if (bookQuantity != other.bookQuantity) return false
        if (bookName != other.bookName) return false
        if (bookCode != other.bookCode) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookId?.hashCode() ?: 0
        result = 31 * result + genre.hashCode()
        result = 31 * result + bookPublisher.hashCode()
        result = 31 * result + bookPrice.hashCode()
        result = 31 * result + bookDescription.hashCode()
        result = 31 * result + bookPages
        result = 31 * result + bookQuantity
        result = 31 * result + bookName.hashCode()
        result = 31 * result + bookCode.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}