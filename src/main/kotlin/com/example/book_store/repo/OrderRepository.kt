package com.example.book_store.repo

import com.example.book_store.models.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepository:CrudRepository<Order,Int> {
}