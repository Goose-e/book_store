package com.example.book_store.repo

import com.example.book_store.models.CartItem
import com.example.book_store.models.CoreEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CartItemRepository : CrudRepository<CartItem, Int> {
    @Query("select b.bookId from Book b where b.bookCode = :code")
    fun findBookByBookCode(@Param("code") bookCode: String?): Long?

    @Query("SELECT c.cartId FROM Cart c JOIN User u ON c.userId = u.userId  WHERE u.login = :login")
    fun findCartIdByUserLogin(@Param("login") login: String): Long?

    @Query("SELECT ent FROM CartItem c JOIN CoreEntity ent on  ent.coreEntityId = c.cartItemsId  where c.cartItemsCode = :code")
    fun findEntityByItemCode(@Param("code") bookCode: String): CoreEntity?

    @Query("SELECT c from CartItem c where c.cartItemsCode = :code")
    fun findItemByItemCode(@Param("code") code: String): CartItem?
}