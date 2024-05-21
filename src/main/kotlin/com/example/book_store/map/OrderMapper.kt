package com.example.book_store.map

import com.example.book_store.models.Order
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class OrderMapper {
fun createOrderMapper(order:Order, address:String, price:BigDecimal, userId:Long):Order = order.copy(
    userId = userId,
    address = address,
    orderPrice = price
    )
}