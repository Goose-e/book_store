package com.example.book_store.service

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateOrderRequestDto
import com.example.book_store.dto.orderDto.CreateOrderItemList

interface OrderService {
    fun createOrUpdateOrder(createOrderRequestDto: CreateOrderRequestDto): HttpResponseBody<CreateOrderItemList>
}