package com.example.book_store.dto

import java.io.Serializable

data class NewUserDto (
    val login: String,
    val userAge: Int,
    val password: String
    ) : Serializable