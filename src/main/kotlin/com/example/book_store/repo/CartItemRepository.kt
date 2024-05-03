package com.example.book_store.repo

import com.example.book_store.models.CartItem
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CartItemRepository : CrudRepository<CartItem, Int> {
    @Query("select b.bookId from Book b where b.bookCode = :code")
    fun findBookByBookCode(@Param("code") bookCode: String?): Long?

    @Query("SELECT c.cartId FROM Cart c JOIN User u ON c.userId = u.userId  WHERE u.login = :login")
    fun findCartIdByUserLogin(@Param("login") login: String): Long?

}