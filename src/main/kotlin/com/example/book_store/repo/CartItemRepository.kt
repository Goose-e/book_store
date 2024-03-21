package com.example.book_store.repo

import com.example.book_store.models.CartItem
import org.springframework.data.repository.CrudRepository

interface CartItemRepository : CrudRepository<CartItem, Int> {
}