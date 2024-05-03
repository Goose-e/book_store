package com.example.book_store.dto.cartItemDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import java.io.Serializable

data class DeleteCartItemRequestDto(
    val cartItemCode: String?
) : Serializable

data class DeleteCartItemDto(
    val itemCode: String,
) : ResponseDto

data class DeleteCartItemResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<DeleteCartItemDto>(httpRequestId)