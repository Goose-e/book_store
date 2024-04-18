package com.example.book_store.models

import java.io.Serializable

data class NewUserDto (
    val login: String,
    val userAge: Int,
    val password: String
    ) : Serializable