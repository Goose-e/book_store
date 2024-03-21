package com.example.book_store.repo

import com.example.book_store.models.Cart
import org.springframework.data.repository.CrudRepository


interface CartRepository:CrudRepository<Cart,Int> {
}