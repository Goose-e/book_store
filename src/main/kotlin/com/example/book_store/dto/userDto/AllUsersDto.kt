package com.example.book_store.dto.userDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.models.enum.RoleEnum
import com.example.book_store.models.enum.StatusEnum
import java.io.Serializable


data class GetUserDto(
    val login: String,
    val password: String,
    val userRole: RoleEnum,
    val userStatus: StatusEnum,
) : Serializable
data class GetUserDtoDB(
    val login: String,
    val password: String,
    val userRole: RoleEnum,
    val userStatus: StatusEnum,
) : Serializable
data class AllUsersDto(
    val listUsersDto: List<GetUserDto>
) : ResponseDto


data class GetUserResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<AllUsersDto>(httpRequestId)
