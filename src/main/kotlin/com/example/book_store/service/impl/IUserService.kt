package com.example.book_store.service.impl

import com.example.book_store.models.User
import com.example.book_store.requestes.SaveUserRequest
import java.util.*


interface IUserService {
    fun auth()
    fun registration(request: SaveUserRequest)
    fun getInfo(id:Long): Optional<User>


}