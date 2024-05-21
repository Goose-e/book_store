package com.example.book_store.map

import com.example.book_store.dto.orderDto.CreateOrderItemDto
import com.example.book_store.dto.orderDto.GetCartItemDB
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.OrderItem
import com.example.book_store.service.GenerationService
import org.springframework.stereotype.Component

@Component
class OrderItemMapper {

    fun mapCartItemToOrderItem(cartItem: GetCartItemDB, orderId: Long?, coreEntity: CoreEntity): OrderItem = OrderItem(
        orderItemId = coreEntity.coreEntityId,
        orderId = orderId,
        bookId = cartItem.bookId,
        orderItemCode = GenerationService.generateCode(),
        orderItemPrice = cartItem.orderItemPrice,
        orderItemAmount = cartItem.orderItemAmount,
    )

    fun mapCartItemListToOrderItemList(cartItem: GetCartItemDB, bookName: String): CreateOrderItemDto =
        CreateOrderItemDto(
            bookName = bookName,
            bookPrice = cartItem.orderItemPrice,
            bookQuantity = cartItem.orderItemAmount

        )
    fun mapOrderItemDtoToOrderItem(item:GetCartItemDB, coreEntity: CoreEntity, orderId: Long?):OrderItem = OrderItem(
        orderItemId = coreEntity.coreEntityId,
        orderId = orderId,
        bookId = item.bookId,
        orderItemCode = GenerationService.generateCode(),
        orderItemPrice = item.orderItemPrice,
        orderItemAmount = item.orderItemAmount,

    )
}