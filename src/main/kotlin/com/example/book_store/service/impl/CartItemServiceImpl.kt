package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.CartDao
import com.example.book_store.dao.CartItemDao
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.*
import com.example.book_store.map.CartItemMapper
import com.example.book_store.models.CartItem
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.StatusEnum.CART_ITEM_ACTUAL
import com.example.book_store.service.CartItemService
import com.example.book_store.service.CoreEntityService
import com.example.book_store.service.GenerationService
import org.dbs.validator.ErrorInfo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*


@Service
class CartItemServiceImpl(
    val cartItemDao: CartItemDao,
    val coreEntityDao: CoreEntityDao,
    val cartItemMapper: CartItemMapper,
    val cartDao: CartDao,
    val coreEntityService: CoreEntityService
) : CartItemService {
    fun convertBase64ToByteArray(base64String: String?): ByteArray {
        if (base64String != null) {
            return Base64.getDecoder().decode(base64String)
        } else {
            return ByteArray(-1)
        }
    }

    fun convertImageToBase64(image: ByteArray?): String? {
        return image?.let { Base64.getEncoder().encodeToString(it) }
    }

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
                    cartItemMapper.mapBookFromListToBookDTO(it, convertImageToBase64(it.image))
                }

                val cart = cartItemMapper.cartPrice(price, cartItemDao.findCartById(cartId))
                cartDao.save(cart)
                response.responseEntity = ListCartItemDto(listBookDto = listBookDto, price = price)
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
                    val itemInCart: CartItem? = cartItemDao.findCartItemByBookCode(bookCode, cartId)
                    if (itemInCart != null) {
                        val i: Int? = itemInCart.cartItemQuantity
                        if (i != null) {
                            val item = cartItemMapper.changeItemQuantity(itemInCart, i + 1)
                            cartItemDao.save(item)
                            response.responseEntity = cartItemMapper.createCartItemDto(item)
                            response.message = "Quantity changed successfully"
                        }
                    } else {
                        val coreEntity = coreEntityService.createCoreEntity(CART_ITEM_ACTUAL)
                        val item = CartItem(
                            cartItemsId = coreEntity.coreEntityId,
                            bookId = bookId,
                            cartId = cartId,
                            cartItemQuantity = 1,
                            cartItemsCode = GenerationService.generateCode()
                        )
                        response.responseEntity = cartItemMapper.createCartItemDto(item)
                        saveToDB(coreEntity, item)
                        response.message = "Item added successfully"
                    }

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
                    if (item.cartItemQuantity!! < changeCartItemQuantityRequestDto.quantity) {
                        if (cartItemDao.findItemCheck(itemCode, changeCartItemQuantityRequestDto.quantity) == null) {
                            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Максимум книг"))
                            response.message = "Too much"
                            if (response.errors.isNotEmpty()) response.responseCode =
                                OC_BUGS else response.responseCode = OC_OK
                            return response
                        }
                    }
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