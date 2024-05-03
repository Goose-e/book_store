package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.LOCALDATETIME_NULL
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.CartItemDao
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateCartItemDto
import com.example.book_store.dto.cartItemDto.CreateCartItemRequestDto
import com.example.book_store.dto.cartItemDto.CreateCartItemResponse
import com.example.book_store.map.CartItemMapper
import com.example.book_store.models.CartItem
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.StatusEnum.CART_ITEM_ACTUAL
import com.example.book_store.service.CartItemService
import com.example.book_store.service.GenerationService
import com.example.book_store.service.GenerationService.Companion.generateEntityId
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime.now


@Service
class CartItemServiceImpl(
    val cartItemDao: CartItemDao,
    val coreEntityDao: CoreEntityDao,
    val cartItemMapper: CartItemMapper
) : CartItemService {
    override fun getAll(): List<CartItem> {
        TODO("Not yet implemented")
    }

    override fun addItemToCart(cartItem: CreateCartItemRequestDto): HttpResponseBody<CreateCartItemDto> {
        val response: HttpResponseBody<CreateCartItemDto> = CreateCartItemResponse()

        cartItem.bookCode.let { bookCode ->
            val bookId = cartItemDao.findBookByBookCode(bookCode)
            bookId?.let { bookId ->
                val authentication: Authentication = SecurityContextHolder.getContext().authentication
                val username = authentication.name
                username?.let {
                    val cartId = cartItemDao.findCartByUserName(username)
                    val coreEntity = CoreEntity(
                        coreEntityId = generateEntityId(),
                        createDate = now(),
                        deleteDate = LOCALDATETIME_NULL,
                        status = CART_ITEM_ACTUAL
                    )
                    val item = CartItem(
                        cartItemsId = coreEntity.coreEntityId,
                        bookId = bookId,
                        cartId = cartId,
                        cartItemsCode = GenerationService.generateCode()
                    )
                    response.responseEntity = cartItemMapper.createCartItemDto(item)
                    saveToDB(coreEntity, item)
                    response.message = "Iteam added successfully"
                }
            } ?: run {
                response.message = "Book not found"
            }
        }
        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }

    @Transactional
    fun saveToDB(coreEntity: CoreEntity, item: CartItem) {
        coreEntityDao.save(coreEntity)
        cartItemDao.save(item)
    }

//    override fun delete(id: Long) {
//       TODO("Not yet implemented")
//    }


}