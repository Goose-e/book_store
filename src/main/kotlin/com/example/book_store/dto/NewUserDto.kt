package com.example.book_store.dto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.models.enum.RoleEnum
import java.io.Serializable

data class NewUserRequestDto(
    val login: String,
    val userAge: Int,
    val password: String
):Serializable

data class NewUserDto (
    val login: String,
    val userAge: Int,
    val userRole:RoleEnum,
    val password: String
    ) : ResponseDto

data class CreateUserResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<NewUserDto>(httpRequestId)