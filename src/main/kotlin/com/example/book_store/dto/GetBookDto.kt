package com.example.book_store.dto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.models.enum.GenreEnum
import java.io.Serializable
import java.math.BigDecimal

data class GetBookRequestDto(
    val bookName:String?,
) : Serializable

data class GetBookDto(
    val genre:GenreEnum ,

    val bookPublisher:String,

    val bookPrice: BigDecimal,

    val bookDescription:String,

    val bookName:String,
) : Serializable

data class ListBookDto(
    val listBookDto: List<GetBookDto?>
):ResponseDto

data class GetBookResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<ListBookDto>(httpRequestId)
