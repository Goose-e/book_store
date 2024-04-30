package com.example.book_store.dto.cartItemDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import java.io.Serializable

data class CreateCartItemRequestDto(
 val bookCode:String?
): Serializable

data class CreateCartItemDto(

    val cartItemsId:Long?,

    val bookId:Long?,

    val cartId:Long?,

    val cartItemsCode:String
):ResponseDto

data class BookDto(
    val bookId:Long,

):Serializable

data class CartDto(
    val cartId:Long,

    ):Serializable

data class CreateCartItemResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<CreateCartItemDto>(httpRequestId)