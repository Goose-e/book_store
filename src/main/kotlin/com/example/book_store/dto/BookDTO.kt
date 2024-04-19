package com.example.book_store.dto

import com.example.book_store.models.enum.GenreEnum
import java.math.BigDecimal

data class BookDTO(val genre:GenreEnum ,

                    val bookPublisher:String,

                    val bookPrice: BigDecimal,

                    val bookDescription:String,

                    val bookPages:Int,

                    val bookQuantity: Int,

                    val bookName:String)
{



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookDTO

        if (genre != other.genre) return false
        if (bookPublisher != other.bookPublisher) return false
        if (bookPrice != other.bookPrice) return false
        if (bookDescription != other.bookDescription) return false
        if (bookPages != other.bookPages) return false
        if (bookQuantity != other.bookQuantity) return false
        if (bookName != other.bookName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = genre.hashCode()
        result = 31 * result + bookPublisher.hashCode()
        result = 31 * result + bookPrice.hashCode()
        result = 31 * result + bookDescription.hashCode()
        result = 31 * result + bookPages
        result = 31 * result + bookQuantity
        result = 31 * result + bookName.hashCode()
        return result
    }
}