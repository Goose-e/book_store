package com.example.book_store.dto

import java.util.UUID

data class UserDto (
    val id:UUID?=null,
    val login:String,
    val password:String,
    val username:String,
    val age:Int,

)