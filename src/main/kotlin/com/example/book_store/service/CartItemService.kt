package com.example.book_store.service

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateCartItemDto
import com.example.book_store.dto.cartItemDto.CreateCartItemRequestDto
import com.example.book_store.models.CartItem

interface CartItemService {
    fun getAll(): List<CartItem>
    fun addItemToCart(cartItem: CreateCartItemRequestDto): HttpResponseBody<CreateCartItemDto>
    //fun delete(id: DeleteCartItemRequestDto): HttpResponseBody<DeleteCartItemRequestDto>
    //fun changeQuantity(book:QuantityRequestDto):HttpResponseBody<QuantityRequestDto>
}