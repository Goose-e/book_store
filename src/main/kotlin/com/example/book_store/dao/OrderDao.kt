package com.example.book_store.dao

import com.example.book_store.models.Order
import com.example.book_store.repo.OrderRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal


@Component
class OrderDao(private val orderRepo: OrderRepository) {
    fun findCartPriceByUserName(login: String): BigDecimal? = orderRepo.findCartByUserName(login)
    fun findUserId(login: String): Long = orderRepo.findUserId(login)
    fun save(order: Order): Order = orderRepo.save(order)
}