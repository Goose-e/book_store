package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException


enum class RoleEnum(
    private val roleId: Int,

    private val roleCode: String,

    private val roleName: String
) {
    USER(0, "USER", "USER"),
    ADMIN(1, "ADMIN", "ADMIN")
    ;

    companion object {
        fun getEnum(roleId: Int): RoleEnum =
            entries.find { it.getId() == roleId } ?: throw UnknownEnumException("privilegeId = $roleId")
    }

    fun getId() = this.roleId
    fun getCode() = this.roleCode
    fun getName() = this.roleName
}