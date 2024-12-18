package com.example.book_store.controller

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateOrderRequestDto

import com.example.book_store.dto.orderDto.CreateOrderItemList
import com.example.book_store.dto.orderDto.CreateOrderItemRequestDto
import com.example.book_store.dto.orderDto.ListAllOrders
import com.example.book_store.dto.orderDto.ListOrders
import com.example.book_store.service.OrderService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1/order")
class OrderController(
    private val orderService: OrderService,
) {
//    protected val logger: Log = LogFactory.getLog(this.javaClass)

    @PostMapping("/addOrder")
    fun createOrder(@RequestBody orderDto: CreateOrderRequestDto): HttpResponseBody<CreateOrderItemList> {
        return orderService.createOrUpdateOrder(orderDto)
    }
    @GetMapping("/getOrderList")
    fun getUserOrderList(): HttpResponseBody<ListOrders> {
        return orderService.getAllOrdersOfUser()
    }


}