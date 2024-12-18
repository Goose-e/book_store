package com.example.book_store.service

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateOrderRequestDto

import com.example.book_store.dto.orderDto.CreateOrderItemList
import com.example.book_store.dto.orderDto.GetOrdersResponse
import com.example.book_store.dto.orderDto.ListAllOrders
import com.example.book_store.dto.orderDto.ListOrders

interface OrderService {
    fun createOrUpdateOrder(createOrderRequestDto: CreateOrderRequestDto): HttpResponseBody<CreateOrderItemList>
    fun getAllOrdersOfUser(): HttpResponseBody<ListOrders>
    fun getAllOrders(): HttpResponseBody<ListAllOrders>
}