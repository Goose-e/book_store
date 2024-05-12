package com.example.book_store.service

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateOrderDto
import com.example.book_store.dto.cartItemDto.CreateOrderRequestDto

interface OrderService {
    fun createOrder(createOrderRequestDto: CreateOrderRequestDto):HttpResponseBody<CreateOrderDto>
}