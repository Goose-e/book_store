package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class StatusEnum(
    private val statusId: Long,
    private val statusCode: String,
    private val statusName: String
) {
    BOOK_ACTUAL(1, "Book Actual", "Book Actual"),
    USER_ACTUAL(2, "User Actual", "User Actual"),
    ;

    companion object {
        fun getEnum(statusId: Long): StatusEnum =
            entries.find { it.getId() == statusId } ?: throw UnknownEnumException("privilegeId = $statusId")
    }

    fun getId() = this.statusId
    fun getCode() = this.statusCode
    fun getName() = this.statusName
}