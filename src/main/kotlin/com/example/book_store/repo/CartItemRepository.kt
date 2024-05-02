package com.example.book_store.repo

import com.example.book_store.models.CartItem
import com.example.book_store.models.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CartItemRepository : CrudRepository<CartItem, Int> {
    @Query("select b.bookId from Book b where b.bookCode = :code")
    fun findBookByBookCode(@Param("code") bookCode: String?): Long?

    @Query("select c.cartId from Cart c WHERE c.userId = :userId ")
    fun findCartByUserId(@Param("userId")userName: Long?): Long?

    @Query("select u from User u where u.login = :USERNAME")
    fun findByLogin(@Param("USERNAME") login: String?): User?
}