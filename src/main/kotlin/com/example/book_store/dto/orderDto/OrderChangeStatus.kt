package com.example.book_store.dto.orderDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.StatusEnum
import java.io.Serializable

data class OrderChangeStatusRequest(
    val orderCode: String,
) : Serializable
data class OrderChangeStatusDB(
    val coreEntity: CoreEntity,
    val status:StatusEnum
):Serializable
data class NewOrderStatus(
    val newStatus: String,
) : ResponseDto

data class OrderChangeStatus(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<NewOrderStatus>(httpRequestId)