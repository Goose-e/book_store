package com.example.book_store.dto.userDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.models.enum.StatusEnum
import java.io.Serializable

data class NewStatusDtoRequest(
    val login: String,
    val userStatusId: Int,
) : Serializable

data class NewStatusDto(
    val login: String,
    val userStatusId: Int,
) : ResponseDto
data class NewStatusDB(
    val login: String,
    val userStatusId: StatusEnum,
) : Serializable



data class NewStatusResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<NewStatusDto>(httpRequestId)
