package com.example.book_store.requestes

import org.jetbrains.annotations.NotNull

data class SaveUserRequest(
    @get:NotNull
    val username: String,
    @get:NotNull
    val userLogin: String,
    @get:NotNull
    val password: String,
    @get:NotNull
    val userAge: Int,
)
