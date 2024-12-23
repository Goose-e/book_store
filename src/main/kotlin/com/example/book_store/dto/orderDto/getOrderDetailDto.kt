package com.example.book_store.dto.orderDto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.ResponseDto
import com.example.book_store.dto.cartItemDto.GetItemListDto
import com.example.book_store.dto.cartItemDto.ListCartItemDto
import com.example.book_store.models.enum.GenreEnum
import com.example.book_store.models.enum.StatusEnum
import java.io.Serializable
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDateTime
data class GetOrderDetailRequestDto(
    val orderCode:String,
) : Serializable
data class GetOrderItemListDTo(
    val bookName: String,
    val bookCode: String,
    val bookPrice: BigDecimal,
    val itemQuantity: Int,
    val image:String?,
    val bookGenre:String?,

) : Serializable
data class GetOrderItemListDB(
    val bookName: String,
    val bookCode: String,
    val bookPrice: BigDecimal,
    val itemQuantity: Int,
    val image:ByteArray?,
    val bookGenre:GenreEnum,
) : Serializable
data class ListOrderDetail(
    val listBookDto: List<GetOrderItemListDTo>,
    val price: BigDecimal,
    val orderStatus:String,
) : ResponseDto

data class GetOrderListResponse(
    private val httpRequestId: RequestId = EMPTY_STRING
) : HttpResponseBody<ListOrderDetail>(httpRequestId)
