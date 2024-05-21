package com.example.book_store.service

import com.example.book_store.dao.OrderItemDao
import com.example.book_store.map.OrderItemMapper

interface OrderItemService {
    val orderItemDao: OrderItemDao
    val orderItemMapper: OrderItemMapper
}
