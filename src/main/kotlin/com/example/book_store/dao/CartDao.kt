package com.example.book_store.dao

import com.example.book_store.models.Cart
import com.example.book_store.repo.CartRepository
import org.springframework.stereotype.Component

@Component
class CartDao(private val cartRepo: CartRepository) {
    fun save(cart: Cart) = cartRepo.save(cart)
}