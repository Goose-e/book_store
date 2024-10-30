package com.example.book_store.service

import com.example.book_store.dto.*
import com.example.book_store.dto.userDto.*
import com.example.book_store.exceptions.UserNotFoundException
import com.example.book_store.models.User
import org.springframework.http.ResponseEntity


interface IUserService {
    fun authenticateUser(loginRequest: LoginUserDto): ResponseEntity<*>
    fun getAllUsers(): HttpResponseBody<AllUsersDto>
    fun registerUser(newUserDto: NewUserRequestDto): HttpResponseBody<NewUserDto>

    @Throws(UserNotFoundException::class)
    fun getUserById(userId: Long?): ResponseEntity<UserDto?>?

    @Throws(UserNotFoundException::class)
    fun updateUser(
        userId: Long?,
        userDetails: UserDto?
    ): ResponseEntity<UserDto?>?
}



