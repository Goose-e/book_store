package com.example.book_store.service.impl

import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dao.OrderItemDao
import com.example.book_store.map.OrderItemMapper
import com.example.book_store.service.OrderItemService
import org.springframework.stereotype.Service

@Service
class OrderItemServiceImpl(
    val coreEntityDao: CoreEntityDao,
    override val orderItemDao: OrderItemDao,
    override val orderItemMapper: OrderItemMapper,
) : OrderItemService