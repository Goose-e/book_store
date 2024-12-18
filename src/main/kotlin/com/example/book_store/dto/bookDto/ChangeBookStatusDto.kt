package com.example.book_store.dto.bookDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.models.enum.StatusEnum
import java.io.Serializable

data class ChangeBookStatusRequestDto(
    val bookCode: String?,
    val bookStatusId: Int
) : Serializable

data class ChangeBookStatusDto(
    val bookName: String,
    val bookCode: String,
    val bookStatusEnum: StatusEnum
) : ResponseDto

data class DeleteBookDtoDB(
    val bookName: String,
    val bookCode: String,
    val bookStatusEnum: StatusEnum
) : Serializable


data class DelBookResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<ChangeBookStatusDto>(httpRequestId)
