package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
import com.example.book_store.constant.SysConst.LOCALDATETIME_NULL
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.CartItemDao
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.*
import com.example.book_store.map.CartItemMapper
import com.example.book_store.models.CartItem
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.StatusEnum.CART_ITEM_ACTUAL
import com.example.book_store.service.CartItemService
import com.example.book_store.service.GenerationService
import com.example.book_store.service.GenerationService.Companion.generateEntityId
import org.dbs.validator.ErrorInfo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime.now


@Service
class CartItemServiceImpl(
    val cartItemDao: CartItemDao,
    val coreEntityDao: CoreEntityDao,
    val cartItemMapper: CartItemMapper
) : CartItemService {
    override fun getAll(): HttpResponseBody<ListCartItemDto> {

        val response: HttpResponseBody<ListCartItemDto> = GetItemListResponse()
        var price: BigDecimal = BigDecimal.ZERO
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name
        username?.let {
            val cartId = cartItemDao.findCartByUserName(username)
            val getBook: MutableCollection<GetItemListDtoDB> = cartItemDao.findAllItems(cartId)
            if (getBook.isNotEmpty()) {
                val listBookDto = getBook.map {
                    price += it.bookPrice
                    cartItemMapper.mapBookFromListToBookDTO(it)
                }
                response.responseEntity = ListCartItemDto(listBookDto = listBookDto,price = price)
                response.message = "Books"
            } else {
                response.message = "Cart is empty"
                response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Cart is empty"))
            }

            if (response.errors.isNotEmpty()) {
                response.responseCode = OC_BUGS
            } else {
                response.responseCode = OC_OK
            }
        }
        return response
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
                        cartItemQuantity = 1,
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

    override fun delete(cartItemRequestDto: DeleteCartItemRequestDto): HttpResponseBody<DeleteCartItemDto> {
        val response: HttpResponseBody<DeleteCartItemDto> = DeleteCartItemResponse()
        cartItemRequestDto.cartItemCode?.let { cartItemCode ->
            cartItemDao.findEntityByItemCode(cartItemCode)?.let { item ->
                val delItem = cartItemMapper.delCartItemDto(cartItemDao.findItemByItemCode(cartItemCode))
                coreEntityDao.save(cartItemMapper.deleteCartItem(item))
                response.responseEntity = delItem
                response.message = "Item Delete Successfully "
            } ?: run {
                response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Item not found"))
                response.message = "Item not found"
            }

        } ?: run {
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Code is null"))
            response.message = "Code is null"
        }
        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }

    override fun changeQuantity(changeCartItemQuantityRequestDto: ChangeCartItemQuantityRequestDto): HttpResponseBody<ChangeCartItemQuantityDto> {
        val response: HttpResponseBody<ChangeCartItemQuantityDto> = ChangeCartItemQuantityResponse()
        changeCartItemQuantityRequestDto.cartItemCode?.let { itemCode ->
            if (changeCartItemQuantityRequestDto.quantity < 0) {
                response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Quantity < 0"))
                response.message = "Quantity is negative"
            } else {
                cartItemDao.findItemByItemCode(itemCode)?.let { item ->
                    val changeItem = cartItemMapper.changeItemQuantity(item, changeCartItemQuantityRequestDto.quantity)
                    cartItemDao.save(changeItem)
                    response.responseEntity = cartItemMapper.changeItemQuantityDto(changeItem)
                    response.message = "Quantity changed"
                }
            }
                ?: run {
                    response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Item not found"))
                    response.message = "Item not found"
                }
        } ?: run {
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Code is null"))
            response.message = "Code is null"
        }
        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }


}