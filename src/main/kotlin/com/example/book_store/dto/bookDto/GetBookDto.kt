package com.example.book_store.dto.bookDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.models.enum.GenreEnum
import com.example.book_store.models.enum.StatusEnum
import java.io.Serializable
import java.math.BigDecimal

data class GetBookRequestDto(
    val bookName: String,
) : Serializable

data class GetBookCodeRequestDto(
    val bookCode: String,
) : Serializable

data class GetBookDto(
    val genre: GenreEnum,
    val bookPublisher: String,
    val bookPrice: BigDecimal,
    val bookDescription: String,
    val bookName: String,
    val bookStatusEnum: StatusEnum,
    val image: String?
) : Serializable


data class GetBookDtoDB(
    val genre: GenreEnum,
    val bookPublisher: String,
    val bookPrice: BigDecimal,
    val bookDescription: String,
    val bookName: String,
    val bookStatusEnum: StatusEnum,
    val image: ByteArray?,
    val code:String
) : Serializable

data class GetBookCodeDto(
    val genre: GenreEnum,
    val bookPublisher: String,
    val bookPrice: BigDecimal,
    val bookDescription: String,
    val bookName: String,
    val bookCode: String,
    val image: String?
) : ResponseDto

data class ListBookDto(
    val listBookDto: List<GetBookDto>
) : ResponseDto

data class GetBookCodeResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<GetBookCodeDto>(httpRequestId)

data class GetBookResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<ListBookDto>(httpRequestId)
