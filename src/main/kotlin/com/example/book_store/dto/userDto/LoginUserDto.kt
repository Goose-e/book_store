package com.example.book_store.dto.userDto

import java.io.Serializable

data class LoginUserDto(
    val login: String?,
    val password: String
) : Serializable