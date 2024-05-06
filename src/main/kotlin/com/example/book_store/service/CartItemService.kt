package com.example.book_store.service

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.*
import com.example.book_store.models.CartItem

interface CartItemService {
    fun getAll(): List<CartItem>
    fun addItemToCart(cartItem: CreateCartItemRequestDto): HttpResponseBody<CreateCartItemDto>
    fun delete(cartItemRequestDto: DeleteCartItemRequestDto): HttpResponseBody<DeleteCartItemDto>
    fun changeQuantity(changeCartItemQuantityRequestDto:ChangeCartItemQuantityRequestDto):HttpResponseBody<ChangeCartItemQuantityDto>
}