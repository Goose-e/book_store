package com.example.book_store.dto

import com.example.book_store.models.enum.RoleEnum


data class UserDto (
    val login:String,
    val password:String,
    val userAge:Int,
    val userRole: RoleEnum
){
    constructor() : this("","",-1,RoleEnum.USER)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserDto

        if (login != other.login) return false
        if (password != other.password) return false
        if (userAge != other.userAge) return false
        if (userRole != other.userRole) return false

        return true
    }

    override fun hashCode(): Int {
        var result = login.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + userAge
        result = 31 * result + userRole.hashCode()
        return result
    }

}