package com.example.book_store.dao

import com.example.book_store.dto.orderDto.GetAllOrdersDB
import com.example.book_store.dto.orderDto.GetOrderItemListDB
import com.example.book_store.dto.orderDto.OrderChangeStatusDB
import com.example.book_store.models.Cart
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.Order
import com.example.book_store.models.enum.StatusEnum
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
    fun findOrderByOrderCode(orderCode: String): MutableCollection<GetOrderItemListDB> = orderRepo.getOrderByOrderCode(orderCode)
    fun getOrderStatusByCode(orderCode:String):StatusEnum = orderRepo.getOrderStatusByCode(orderCode)
    fun getOrderByOrderEntCodeForChangeStatus(orderCode: String): OrderChangeStatusDB? = orderRepo.getOrderByOrderCodeForChangeStatus(orderCode)
    }
