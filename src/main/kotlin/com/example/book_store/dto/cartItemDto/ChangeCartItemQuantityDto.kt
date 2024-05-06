package com.example.book_store.dto.cartItemDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import java.io.Serializable

data class ChangeCartItemQuantityRequestDto(
    val cartItemCode: String?,
    val quantity: Int
) : Serializable

data class ChangeCartItemQuantityDto(
    val itemCode: String,
    val quantity: Int?
) : ResponseDto

data class ChangeCartItemQuantityResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<ChangeCartItemQuantityDto>(httpRequestId)