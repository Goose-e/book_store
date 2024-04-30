package com.example.book_store.dto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.models.enum.StatusEnum
import java.io.Serializable

data class DeleteBookRequestDto(
    val bookCode: String?,
) : Serializable

data class DeleteBookDto(

    val bookName:String,

    val bookCode:String,
    val bookStatusEnum: StatusEnum
) : ResponseDto
data class DeleteBookDtoDB(
    val bookName:String,
    val bookCode:String,
    val bookStatusEnum: StatusEnum
) : Serializable


data class DelBookResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<DeleteBookDto>(httpRequestId)
