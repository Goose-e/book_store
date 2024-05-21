package com.example.book_store.controller

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.userDto.LoginUserDto
import com.example.book_store.dto.userDto.NewUserDto
import com.example.book_store.dto.userDto.NewUserRequestDto
import com.example.book_store.service.IUserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: IUserService
) {


    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginUserDto): ResponseEntity<*> =
        userService.authenticateUser(loginRequest)


    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody newUserDto: NewUserRequestDto): HttpResponseBody<NewUserDto> =
        userService.registerUser(newUserDto)


}
