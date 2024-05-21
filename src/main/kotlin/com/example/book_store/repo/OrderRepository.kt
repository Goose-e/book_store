package com.example.book_store.repo

import com.example.book_store.models.Cart
import com.example.book_store.models.Order
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface OrderRepository : CrudRepository<Order, Long> {
    @Query("SELECT c.cartPrice FROM Cart c JOIN User u ON c.userId = u.userId  WHERE u.login = :login")
    fun findCartPriceByUserName(@Param("login") login: String): BigDecimal?

    @Query("SELECT c FROM Cart c JOIN User u ON c.userId = u.userId  WHERE u.login = :login")
    fun findCartByUserName(@Param("login") login: String): Cart?

    @Query("SELECT u.userId FROM User u where u.login = :login")
    fun findUserId(@Param("login") login: String): Long
}