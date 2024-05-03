package com.example.book_store.map

import com.example.book_store.dto.cartItemDto.CreateCartItemDto
import com.example.book_store.dto.cartItemDto.DeleteCartItemDto
import com.example.book_store.models.CartItem
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.StatusEnum
import org.springframework.stereotype.Component

@Component
class CartItemMapper {

    fun createCartItemDto(item: CartItem): CreateCartItemDto =
        CreateCartItemDto(
            cartItemsCode = item.cartItemsCode,
        )
    fun deleteCartItem(ent:CoreEntity): CoreEntity = ent.copy(
        status = StatusEnum.CART_ITEM_CLOSED
    )
    fun delCartItemDto(item: CartItem): DeleteCartItemDto =
        DeleteCartItemDto(
            itemCode = item.cartItemsCode,
        )
}