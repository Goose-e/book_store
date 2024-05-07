package com.example.book_store.dao

import com.example.book_store.models.CartItem
import com.example.book_store.repo.CartItemRepository
import com.example.book_store.repo.CartRepository
import org.springframework.stereotype.Component

@Component
class CartItemDao(private val cartItemRepository: CartItemRepository,
    private val cartRepository: CartRepository
) {
    fun findBookByBookCode(bookCode: String?) = cartItemRepository.findBookByBookCode(bookCode)
    fun save(cartItem: CartItem) = cartItemRepository.save(cartItem)
    fun findCartByUserName(login:String) = cartItemRepository.findCartIdByUserLogin(login)
    fun findEntityByItemCode(Code:String) = cartItemRepository.findEntityByItemCode(Code)
    fun findItemByItemCode(Code:String) = cartItemRepository.findItemByItemCode(Code)
    fun findAllItems(cartId:Long?) = cartItemRepository.getAllItems(cartId)
    fun findCartById(cartId: Long?) = cartRepository.findById(cartId)
}