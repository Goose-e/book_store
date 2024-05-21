package com.example.book_store.dao

import com.example.book_store.models.OrderItem
import com.example.book_store.repo.BookRepository
import com.example.book_store.repo.CartItemRepository
import com.example.book_store.repo.OrderItemRepository
import org.springframework.stereotype.Component

@Component
class OrderItemDao(
    private val orderItemRepo: OrderItemRepository,
    private val cartItemRepository: CartItemRepository,
    private val bookRepository: BookRepository
) {
    fun findCartByUserName(login: String) = cartItemRepository.findCartIdByUserLogin(login)
    fun findAllItems(cartId: Long?) = orderItemRepo.getAllItems(cartId)
    fun save(orderItem: OrderItem): OrderItem = orderItemRepo.save(orderItem)
    fun findBookNameByBookId(bookId: Long) = bookRepository.findBookNameById(bookId)
    fun saveAll(orderItems: List<OrderItem>) = orderItemRepo.saveAll(orderItems)
}