package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class StatusEnum(
    private val statusId: Int,
    private val statusCode: String,
    private val statusName: String
) {

    BOOK_ACTUAL(0, "BOOK_CODE", "Book Actual"),
    USER_ACTUAL(1, "USER_ACTUAL", "User Actual"),
    BOOK_CLOSED(2, "BOOK_CLOSED", "Book Closed"),
    USER_CLOSED(3, "USER_CLOSED", "User Closed"),
    CART_ITEM_ACTUAL(4, "ITEM_ACTUAL", "Item Actual"),
    CART_ITEM_CLOSED(5, "ITEM_CLOSED", "Item Closed"),
    ORDER_ACTUAL(6, "ORDER_ACTUAL", "Order Actual"),
    ORDER_CLOSED(7, "ORDER_CLOSED", "Order Closed"),
    ORDER_ITEM_ACTUAL(8, "ORDER_ITEM_ACTUAL", "Order Item Actual"),
    ORDER_ITEM_CLOSED(9, "ORDER_ITEM_CLOSED", "Order Item Closed"),
    ;

    companion object {
        fun getEnum(statusId: Int): StatusEnum =
            entries.find { it.getId() == statusId } ?: throw UnknownEnumException("privilegeId = $statusId")

        fun getEnumByCode(statusCode: String): StatusEnum =
            entries.find { it.getCode() == statusCode } ?: throw UnknownEnumException("privilegeId = $statusCode")
    }

    fun getId() = this.statusId
    fun getCode() = this.statusCode
    fun getName() = this.statusName
}