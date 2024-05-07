package com.example.book_store.dto.cartItemDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.models.enum.GenreEnum
import java.io.Serializable
import java.math.BigDecimal


data class GetItemListDto(
    val bookName: String,
    val bookGenre:GenreEnum,
    val bookCode: String,
    val itemQuantity: Int,
    val itemCode: String,
    val bookPrice: BigDecimal,
) : Serializable

data class GetItemListDtoDB(
    val bookName: String,
    val bookGenre: GenreEnum,
    val bookCode: String,
    val bookPrice: BigDecimal,
    val itemCode: String,
    val itemQuantity:Int,
) : Serializable

data class ListCartItemDto(
    val listBookDto: List<GetItemListDto>,
    val price: BigDecimal
) : ResponseDto

data class GetItemListResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<ListCartItemDto>(httpRequestId)
