package com.example.book_store.repo

import com.example.book_store.dto.cartItemDto.BookDto
import com.example.book_store.dto.cartItemDto.CartDto
import com.example.book_store.models.CartItem
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CartItemRepository : CrudRepository<CartItem, Int>
{
    @Query("select new com.example.book_store.dto.cartItemDto.BookDto(b.bookId) from Book b where b.bookCode = :code")
    fun findBookByBookCode(@Param("code") bookCode:String?): BookDto?

    @Query("select new com.example.book_store.dto.cartItemDto.CartDto(c.cartId) from Cart c WHERE c.userId = :userId ")
    fun findCartByUserId(@Param("userId") userId: Long?): CartDto
}