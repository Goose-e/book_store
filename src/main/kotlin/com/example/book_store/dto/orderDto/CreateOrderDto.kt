package com.example.book_store.dto.cartItemDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

data class CreateOrderRequestDto(
    val address: String?,
) : Serializable

data class CreateOrderDto(
    val address: String,
    val orderPrice: BigDecimal,
    val orderDate: LocalDateTime,
    val orderCode: String
) : ResponseDto

data class CreateOrderResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<CreateOrderDto>(httpRequestId)
