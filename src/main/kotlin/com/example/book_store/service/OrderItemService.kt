package com.example.book_store.service

import com.example.book_store.dto.orderDto.CreateOrderItemList
import com.example.book_store.dto.orderDto.CreateOrderItemRequestDto

interface OrderItemService {
    fun createOrderItem(orderId: CreateOrderItemRequestDto): CreateOrderItemList?
}
