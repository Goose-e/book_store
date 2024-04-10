package com.example.book_store.controller

import com.example.book_store.models.User
import com.example.book_store.requestes.SaveUserRequest
import com.example.book_store.service.UserService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/user", produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(private val userService:UserService) {
    @GetMapping("/{id}")
    fun getInfo(@PathVariable("id") id:Long): Optional<User> = userService.getInfo(id)

    @PostMapping
    fun reg(@Valid @RequestBody request: SaveUserRequest):StatusResponse
    {
        userService.registration(request)
        return StatusResponse("Created")
    }
}