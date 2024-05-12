package com.example.book_store.dto.orderDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import java.io.Serializable
import java.math.BigDecimal

data class CreateOrderItemRequestDto(
    val orderId: Long?,
    val login: String
) : Serializable

data class CreateOrderItemList(
    val listBookDto: List<CreateOrderItemDto>,
) : ResponseDto

data class CreateOrderItemDto(
    val bookName: String,
    val bookPrice: BigDecimal,
    val bookQuantity: Int,
) : Serializable

data class GetCartItemDB(
    val bookId: Long,
    val orderItemPrice: BigDecimal,
    val orderItemAmount: Int
) : Serializable

data class CreateOrderItemResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<CreateOrderItemList>(httpRequestId)