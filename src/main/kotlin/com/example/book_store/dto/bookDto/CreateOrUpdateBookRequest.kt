package com.example.book_store.dto.bookDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.models.enum.GenreEnum
import java.io.Serializable
import java.math.BigDecimal

data class CreateOrUpdateBookRequestDto(
    val genre: GenreEnum,

    val bookPublisher:String,

    val bookPrice: String,

    val bookDescription:String,

    val bookPages:Int,

    val bookQuantity: Int,

    val bookName:String,

    val bookCode:String?
) : Serializable

data class CreatedBookDto(
    val genre:GenreEnum ,

    val bookPublisher:String,

    val bookPrice: BigDecimal,

    val bookDescription:String,

    val bookPages:Int,

    val bookQuantity: Int,

    val bookName:String,

    val bookCode:String
) : ResponseDto


data class CreateOrUpdateBookResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<CreatedBookDto>(httpRequestId)
