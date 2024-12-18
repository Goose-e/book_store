package com.example.book_store.controller

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.bookDto.ListBookDto
import com.example.book_store.dto.orderDto.ListAllOrders
import com.example.book_store.dto.userDto.*
import com.example.book_store.service.BookService
import com.example.book_store.service.IUserService
import com.example.book_store.service.OrderService
import com.example.book_store.service.impl.BookServiceImpl
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1/userManagement")
class AdminController(
    private val userService: IUserService,
    private val orderService: OrderService,
    private val bookService: BookService
) {


    @GetMapping("/getAllUsers")
    fun getAllUsers(): HttpResponseBody<AllUsersDto> = userService.getAllUsers()


    @GetMapping("/signup")
    fun registerUser(@Valid @RequestBody newUserDto: NewUserRequestDto): HttpResponseBody<NewUserDto> =
        userService.registerUser(newUserDto)

    @PostMapping("/updateUserStatus")
    fun updateUserStatus(@Valid @RequestBody newStatusDto: NewStatusDtoRequest): HttpResponseBody<NewStatusDto>
    = userService.newUserStatus(newStatusDto)

    @GetMapping("/getAllOrders")
    fun getAllOrders(): HttpResponseBody<ListAllOrders> {
        return orderService.getAllOrders()
    }
    @GetMapping("/getAllBlockedBooks")
    fun getAllBlockedBooks(): HttpResponseBody<ListBookDto> {
        return bookService.getAllBlockedBooks()
    }

}
