package com.example.book_store.controller

import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateCartItemDto
import com.example.book_store.dto.cartItemDto.CreateCartItemRequestDto
import com.example.book_store.dto.cartItemDto.DeleteCartItemDto
import com.example.book_store.dto.cartItemDto.DeleteCartItemRequestDto
import com.example.book_store.service.CartItemService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/v1/cart")
class CartItemController(
    private val cartItemService: CartItemService
)
{
//    protected val logger: Log = LogFactory.getLog(this.javaClass)

    @PostMapping("/addToCart")
    fun addToCart(@RequestBody cartItem: CreateCartItemRequestDto):HttpResponseBody<CreateCartItemDto>{
        return cartItemService.addItemToCart(cartItem)
    }
    @PostMapping("/delFromCart")
    fun delFromCart(@RequestBody cartItem: DeleteCartItemRequestDto):HttpResponseBody<DeleteCartItemDto>{
        return cartItemService.delete(cartItem)
    }
}