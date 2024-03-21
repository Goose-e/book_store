package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException


enum class RoleRefEnum(
    private val roleId: Long,

    private val roleCode: String,

    private val roleName: String
) {
    ;

    companion object {
        fun getEnum(roleId: Long): RoleRefEnum =
            entries.find { it.getId() == roleId } ?: throw UnknownEnumException("privilegeId = $roleId")
    }

    fun getId() = this.roleId
    fun getCode() = this.roleCode
    fun getName() = this.roleName
}