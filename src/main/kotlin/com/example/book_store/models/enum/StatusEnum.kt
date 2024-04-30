package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class StatusEnum(
    private val statusId: Int,
    private val statusCode: String,
    private val statusName: String
) {
    // TODO: delete auto set id in db
    BOOK_ACTUAL(0, "BOOK_CODE", "Book Actual"),
    USER_ACTUAL(1, "USER_ACTUAL", "User Actual"),
    BOOK_UNAVAILABLE(2, "BOOK_UNAVAILABLE", "Book Unavailable"),
    USER_CLOSED(3, "USER_CLOSED", "User Closed"),
    USER_ITEM(4, "ITEM_ACTUAL", "Item Actual"),
    ;

    companion object {
        fun getEnum(statusId: Int): StatusEnum =
            entries.find { it.getId() == statusId } ?: throw UnknownEnumException("privilegeId = $statusId")
    }

    fun getId() = this.statusId
    fun getCode() = this.statusCode
    fun getName() = this.statusName
}