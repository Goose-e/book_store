package com.example.book_store.dto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import java.io.Serializable

data class CreateOrUpdateBookRequestDto(
    // change
    val code: String?
) : Serializable

data class CreatedBookDto(
    // change
) : ResponseDto

data class CreateOrUpdateBookResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<CreatedBookDto>(httpRequestId)