package com.example.book_store.dto

import java.io.Serializable

data class LoginUserDto (
    val username: String,
    var userAge:Int,
    val password: String
    ):Serializable