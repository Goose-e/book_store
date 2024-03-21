package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class StatusesRefEnum(
    private val statusId: Long,
    private val statusCode: String,
    private val statusName: String
) {
    ;

    companion object {
        fun getEnum(statusId: Long): StatusesRefEnum =
            entries.find { it.getId() == statusId } ?: throw UnknownEnumException("privilegeId = $statusId")
    }

    fun getId() = this.statusId
    fun getCode() = this.statusCode
    fun getName() = this.statusName
}