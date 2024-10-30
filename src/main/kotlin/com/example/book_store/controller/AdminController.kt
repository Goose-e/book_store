package com.example.book_store.controller

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.userDto.AllUsersDto
import com.example.book_store.dto.userDto.NewUserDto
import com.example.book_store.dto.userDto.NewUserRequestDto
import com.example.book_store.service.IUserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user_management")
class AdminController(
    private val userService: IUserService
) {


    @GetMapping("/getAllUsers")
    fun getAllUsers(): HttpResponseBody<AllUsersDto> = userService.getAllUsers()


    @GetMapping("/signup")
    fun registerUser(@Valid @RequestBody newUserDto: NewUserRequestDto): HttpResponseBody<NewUserDto> =
        userService.registerUser(newUserDto)


}
