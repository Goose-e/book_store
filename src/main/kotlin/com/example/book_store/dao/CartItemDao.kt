package com.example.book_store.dao

import com.example.book_store.models.CartItem
import com.example.book_store.repo.CartItemRepository
import org.springframework.stereotype.Component

@Component
class CartItemDao(private val cartRepo:CartItemRepository)
{
    fun findBookByBookCode(bookCode:String?) = cartRepo.findBookByBookCode(bookCode)
    fun findCartByUserId(userId:Long?) = cartRepo.findCartByUserId(userId)
    fun save(cartItem: CartItem) = cartRepo.save(cartItem)
}