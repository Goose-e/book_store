package com.example.book_store.service

import com.example.book_store.dto.*
import com.example.book_store.dto.userDto.LoginUserDto
import com.example.book_store.dto.userDto.NewUserDto
import com.example.book_store.dto.userDto.NewUserRequestDto
import com.example.book_store.dto.userDto.UserDto
import com.example.book_store.exceptions.UserNotFoundException
import com.example.book_store.models.User
import org.springframework.http.ResponseEntity


interface IUserService{
    fun authenticateUser(loginRequest: LoginUserDto): ResponseEntity<*>
    fun getAllUsers(): MutableIterable<User>
    fun registerUser(newUserDto: NewUserRequestDto): HttpResponseBody<NewUserDto>
    @Throws(UserNotFoundException::class)
    fun getUserById(userId: Long?): ResponseEntity<UserDto?>?

    @Throws(UserNotFoundException::class)
    fun updateUser(
        userId: Long?,
        userDetails: UserDto?
    ): ResponseEntity<UserDto?>?
   }



