package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.LOCALDATETIME_NULL
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dao.OrderDao
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateOrderRequestDto
import com.example.book_store.dto.orderDto.CreateOrderItemList
import com.example.book_store.dto.orderDto.CreateOrderItemResponse
import com.example.book_store.dto.orderDto.GetCartItemDB
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.Order
import com.example.book_store.models.enum.StatusEnum.ORDER_ACTUAL
import com.example.book_store.models.enum.StatusEnum.ORDER_ITEM_ACTUAL
import com.example.book_store.service.GenerationService
import com.example.book_store.service.GenerationService.Companion.generateEntityId
import com.example.book_store.service.OrderItemService
import com.example.book_store.service.OrderService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime.now

@Service
class OrderServiceImpl(
    val orderDao: OrderDao,
    val coreEntityDao: CoreEntityDao,
    val orderItemService: OrderItemService,

    ) : OrderService {

    override fun createOrder(createOrderRequestDto: CreateOrderRequestDto): HttpResponseBody<CreateOrderItemList> {
        val response: HttpResponseBody<CreateOrderItemList> = CreateOrderItemResponse()
        createOrderRequestDto.address?.let {
            val authentication: Authentication = SecurityContextHolder.getContext().authentication
            val username = authentication.name
            username?.let {
                val price = orderDao.findCartPriceByUserName(username)
                val userId = orderDao.findUserId(username)
                price?.let {
                    val coreEntity = CoreEntity(
                        coreEntityId = generateEntityId(),
                        createDate = now(),
                        deleteDate = LOCALDATETIME_NULL,
                        status = ORDER_ACTUAL
                    )
                    val order = Order(
                        orderId = coreEntity.coreEntityId,
                        userId = userId,
                        address = createOrderRequestDto.address,
                        orderPrice = price,
                        orderDate = now(),
                        orderCode = GenerationService.generateCode()
                    )
                    val cartId = orderItemService.orderItemDao.findCartByUserName(username)
                    val getBook: MutableCollection<GetCartItemDB> = orderItemService.orderItemDao.findAllItems(cartId)
                    var bookName: String
                    if (getBook.isNotEmpty()) {
                        saveInDB(coreEntity = coreEntity, order = order, getBook)

                        val createOrderItem = getBook.map {
                            bookName = orderItemService.orderItemDao.findBookNameByBookId(it.bookId)
                            orderItemService.orderItemMapper.mapCartItemListToOrderItemList(it, bookName)
                        }
                    }
                } ?: run {
                    response.message = "User not authorized to order ${createOrderRequestDto.address}"
                }

            } ?: run {
                response.message = "address is empty"
            }
        }
        if (response.errors.isNotEmpty()) {
            response.responseCode = OC_BUGS
        } else {
            response.responseCode = OC_OK
        }
        return response
    }

    @Transactional
    fun saveInDB(coreEntity: CoreEntity, order: Order, books: MutableCollection<GetCartItemDB>) {
        coreEntityDao.save(coreEntity)
        orderDao.save(order)
        books.map {
            val coreEntity = CoreEntity(
                coreEntityId = generateEntityId(),
                createDate = now(),
                deleteDate = LOCALDATETIME_NULL,
                status = ORDER_ITEM_ACTUAL
            )
            var orderItem =
                orderItemService.orderItemMapper.mapCartItemToOrderItem(it, order.orderId, coreEntity)
            coreEntityDao.save(coreEntity)
            orderItemService.orderItemDao.save(orderItem)
        }
    }


}