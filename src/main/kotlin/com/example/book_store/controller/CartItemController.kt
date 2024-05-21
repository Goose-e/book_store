package com.example.book_store.controller

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.*
import com.example.book_store.service.CartItemService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1/cart")
class CartItemController(
    private val cartItemService: CartItemService
) {
//    protected val logger: Log = LogFactory.getLog(this.javaClass)

    @PostMapping("/addToCart")
    fun addToCart(@RequestBody cartItem: CreateCartItemRequestDto): HttpResponseBody<CreateCartItemDto> {
        return cartItemService.addItemToCart(cartItem)
    }

    @PostMapping("/delFromCart")
    fun delFromCart(@RequestBody cartItem: DeleteCartItemRequestDto): HttpResponseBody<DeleteCartItemDto> {
        return cartItemService.delete(cartItem)
    }

    @PostMapping("/changeQuantity")
    fun changeItemQuantity(@RequestBody cartItem: ChangeCartItemQuantityRequestDto): HttpResponseBody<ChangeCartItemQuantityDto> {
        return cartItemService.changeQuantity(cartItem)
    }

    @GetMapping("/getItemList")
    fun getItemList(): HttpResponseBody<ListCartItemDto> {
        return cartItemService.getAll()
    }
}