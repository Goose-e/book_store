package com.example.book_store.repo

import com.example.book_store.models.OrderItem
import org.springframework.data.repository.CrudRepository

interface OrderItemRepository:CrudRepository<OrderItem,Int>