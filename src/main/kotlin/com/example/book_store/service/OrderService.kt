package com.example.book_store.service

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateOrderRequestDto
import com.example.book_store.dto.orderDto.*
import java.net.http.HttpResponse

interface OrderService {
    fun createOrUpdateOrder(createOrderRequestDto: CreateOrderRequestDto): HttpResponseBody<CreateOrderItemList>
    fun getAllOrdersOfUser(): HttpResponseBody<ListOrders>
    fun getAllOrders(): HttpResponseBody<ListAllOrders>
    fun getOrderDetails(orderDetailRequestDto:GetOrderDetailRequestDto): HttpResponseBody<ListOrderDetail>
    fun changeOrderStatus(orderDetailRequestDto:OrderChangeStatusRequest): HttpResponseBody<NewOrderStatus>

}