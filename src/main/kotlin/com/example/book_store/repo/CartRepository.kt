package com.example.book_store.repo

import com.example.book_store.models.Cart
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param


interface CartRepository : CrudRepository<Cart, Int> {
    @Query("select c from Cart c where c.cartId=:id")
    fun findById(@Param("id") id: Long?): Cart
}