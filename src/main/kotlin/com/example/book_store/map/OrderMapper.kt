package com.example.book_store.map

import com.example.book_store.dto.cartItemDto.GetItemListDto
import com.example.book_store.dto.cartItemDto.GetItemListDtoDB
import com.example.book_store.dto.orderDto.*
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.Order
import com.example.book_store.models.enum.StatusEnum
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class OrderMapper {
    fun createOrderMapper(order: Order, address: String, price: BigDecimal, userId: Long): Order = order.copy(
        userId = userId,
        address = address,
        orderPrice = price
    )

    companion object {
        fun mapDeleteEntToEnt(core: CoreEntity, newStatus: StatusEnum): CoreEntity =
            core.copy(
                status = newStatus
            )
        fun changeOrderDto(newStatus: String): NewOrderStatus = NewOrderStatus(newStatus = newStatus)
    }


    fun getOrdersMapper(order: GetOrdersDB): GetOrdersDTO = GetOrdersDTO(
        address = order.address,
        orderPrice = order.orderPrice,
        orderDate = order.orderDate,
        orderCode = order.orderCode,
        orderStatus = order.orderStatus.getCode()
    )

    fun mapBookFromListToBookDTO(book: GetOrderItemListDB, image: String?): GetOrderItemListDTo =
        GetOrderItemListDTo(
            bookName = book.bookName,
            bookCode = book.bookCode,
            itemQuantity = book.itemQuantity,
            bookPrice = book.bookPrice,
            image = image,
            bookGenre = book.bookGenre.getName(),
        )

    fun getOrdersAllMapper(order: GetAllOrdersDB): GetAllOrdersDTO = GetAllOrdersDTO(
        userName = order.userName,
        address = order.address,
        orderPrice = order.orderPrice,
        orderDate = order.orderDate,
        orderCode = order.orderCode,
        orderStatus = order.orderStatus.getCode()
    )
}