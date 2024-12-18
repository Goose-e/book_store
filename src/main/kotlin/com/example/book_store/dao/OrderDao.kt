package com.example.book_store.dao

import com.example.book_store.dto.orderDto.GetAllOrdersDB
import com.example.book_store.models.Cart
import com.example.book_store.models.Order
import com.example.book_store.repo.OrderRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal


@Component
class OrderDao(private val orderRepo: OrderRepository) {
    fun findCartPriceByUserName(login: String): BigDecimal? = orderRepo.findCartPriceByUserName(login)
    fun findCartByUserName(login: String): Cart? = orderRepo.findCartByUserName(login)
    fun findUserId(login: String): Long = orderRepo.findUserId(login)
    fun findOrderByUserName(login: String) = orderRepo.findOrderByUserName(login)
    fun save(order: Order): Order = orderRepo.save(order)
    fun getAllOrders():MutableCollection<GetAllOrdersDB> = orderRepo.getAllOrders()
}