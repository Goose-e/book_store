package com.example.book_store.map

import com.example.book_store.dto.cartItemDto.CreateCartItemDto
import com.example.book_store.models.CartItem


class CartItemMapper {


    companion object {
        fun CreateCartItemDto(item: CartItem): CreateCartItemDto =
            CreateCartItemDto(
                cartItemsId = item.cartItemsId,

         bookId = item.bookId,

      cartId = item.bookId,

         cartItemsCode = item.cartItemsCode,
            )


    }

}