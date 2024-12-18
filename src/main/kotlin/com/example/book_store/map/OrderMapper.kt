package com.example.book_store.map

import com.example.book_store.dto.orderDto.GetAllOrdersDB
import com.example.book_store.dto.orderDto.GetAllOrdersDTO
import com.example.book_store.dto.orderDto.GetOrdersDB
import com.example.book_store.dto.orderDto.GetOrdersDTO
import com.example.book_store.models.Order
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class OrderMapper {
    fun createOrderMapper(order: Order, address: String, price: BigDecimal, userId: Long): Order = order.copy(
        userId = userId,
        address = address,
        orderPrice = price
    )

    fun getOrdersMapper(order: GetOrdersDB): GetOrdersDTO = GetOrdersDTO(
        address = order.address,
        orderPrice = order.orderPrice,
        orderDate = order.orderDate,
        orderCode = order.orderCode,
        orderStatus = order.orderStatus.getCode()
    )

    fun getOrdersAllMapper(order: GetAllOrdersDB): GetAllOrdersDTO = GetAllOrdersDTO(
        userName = order.userName,
        address = order.address,
        orderPrice = order.orderPrice,
        orderDate = order.orderDate,
        orderCode = order.orderCode,
        orderStatus = order.orderStatus.getCode()
    )
}