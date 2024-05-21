package com.example.book_store.map

import com.example.book_store.dto.cartItemDto.*
import com.example.book_store.models.Cart
import com.example.book_store.models.CartItem
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.StatusEnum
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CartItemMapper {

    fun createCartItemDto(item: CartItem): CreateCartItemDto =
        CreateCartItemDto(
            cartItemsCode = item.cartItemsCode,
        )

    fun deleteCartItem(ent: CoreEntity): CoreEntity = ent.copy(
        status = StatusEnum.CART_ITEM_CLOSED
    )

    fun changeItemQuantity(item: CartItem, quantity: Int): CartItem = item.copy(
        cartItemQuantity = quantity
    )

    fun changeItemQuantityDto(item: CartItem): ChangeCartItemQuantityDto = ChangeCartItemQuantityDto(
        itemCode = item.cartItemsCode,
        quantity = item.cartItemQuantity
    )

    fun delCartItemDto(item: CartItem?): DeleteCartItemDto? =
        item?.let {
            DeleteCartItemDto(
                itemCode = it.cartItemsCode,
            )
        }


    fun cartPrice(price: BigDecimal, cart: Cart): Cart = cart.copy(cartPrice = price)

    fun mapBookFromListToBookDTO(book: GetItemListDtoDB): GetItemListDto =
        GetItemListDto(
            bookName = book.bookName,
            bookGenre = book.bookGenre,
            bookCode = book.bookCode,
            itemQuantity = book.itemQuantity,
            itemCode = book.itemCode,
            bookPrice = book.bookPrice,
        )
}