package com.example.book_store.map

import com.example.book_store.dto.cartItemDto.CreateCartItemDto
import com.example.book_store.models.CartItem
import org.springframework.stereotype.Component

@Component
class CartItemMapper {

    fun createCartItemDto(item: CartItem): CreateCartItemDto =
        CreateCartItemDto(
            cartItemsCode = item.cartItemsCode,
        )

}