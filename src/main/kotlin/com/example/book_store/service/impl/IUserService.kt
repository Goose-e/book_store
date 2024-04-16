package com.example.book_store.service.impl

import com.example.book_store.dto.UserDto
import com.example.book_store.exceptions.UserNotFoundException
import com.example.book_store.models.User
import org.springframework.http.ResponseEntity


interface IUserService{

    fun getAllUsers(): List<User?>?

    @Throws(UserNotFoundException::class)
    fun getUserById(userId: Long?): ResponseEntity<UserDto?>?

    @Throws(UserNotFoundException::class)
    fun updateUser(
        userId: Long?,
        userDetails: UserDto?
    ): ResponseEntity<UserDto?>?
   }



