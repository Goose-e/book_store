package com.example.book_store.dto.orderDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.models.enum.StatusEnum
import java.io.Serializable
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDateTime


data class GetOrdersDB(
    val address: String,
    val orderPrice: BigDecimal,
    val orderDate: LocalDateTime,
    val orderCode: String,
    val orderStatus: StatusEnum
) : Serializable

data class ListOrders(
    val listOrderDto: List<GetOrdersDTO>,
) : ResponseDto

data class GetOrdersDTO(
    val address: String,
    val orderPrice: BigDecimal,
    val orderDate: LocalDateTime,
    val orderCode: String,
    val orderStatus: String,
) : Serializable

data class GetOrdersResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<ListOrders>(httpRequestId)