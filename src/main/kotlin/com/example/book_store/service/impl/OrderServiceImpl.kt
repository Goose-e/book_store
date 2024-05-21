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
import com.example.book_store.models.OrderItem
import com.example.book_store.models.enum.StatusEnum.ORDER_ACTUAL
import com.example.book_store.models.enum.StatusEnum.ORDER_ITEM_ACTUAL
import com.example.book_store.service.GenerationService
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
    val orderItemService: OrderItemService
) : OrderService {

    override fun createOrder(createOrderRequestDto: CreateOrderRequestDto): HttpResponseBody<CreateOrderItemList> {
        val response: HttpResponseBody<CreateOrderItemList> = CreateOrderItemResponse()
        val address = createOrderRequestDto.address
        if (address != null) {
            val authentication: Authentication = SecurityContextHolder.getContext().authentication
            val username = authentication.name
            if (!username.isNullOrBlank()) {
                val userId = orderDao.findUserId(username)
                val price = orderDao.findCartPriceByUserName(username)
                if (price != null) {
                    val orderCoreEntity = CoreEntity(
                        coreEntityId = GenerationService.generateEntityId(),
                        createDate = now(),
                        deleteDate = LOCALDATETIME_NULL,
                        status = ORDER_ACTUAL
                    )
                    val order = Order(
                        orderId = orderCoreEntity.coreEntityId,
                        userId = userId,
                        address = address,
                        orderPrice = price,
                        orderDate = now(),
                        orderCode = GenerationService.generateCode()
                    )
                    val cartId = orderItemService.orderItemDao.findCartByUserName(username)
                    val cartItems: MutableCollection<GetCartItemDB> = orderItemService.orderItemDao.findAllItems(cartId)
                    if (cartItems.isNotEmpty()) {
                        val orderItems = mutableListOf<OrderItem>()
                        val orderItemCoreEntities = mutableListOf<CoreEntity>()
                        cartItems.forEach { cartItem ->
                            val itemCoreEntity = CoreEntity(
                                coreEntityId = GenerationService.generateEntityId(),
                                createDate = now(),
                                deleteDate = LOCALDATETIME_NULL,
                                status = ORDER_ITEM_ACTUAL
                            )
                            orderItemCoreEntities.add(itemCoreEntity)
                            orderItems.add(
                                orderItemService.orderItemMapper.mapOrderItemDtoToOrderItem(
                                    cartItem,
                                    itemCoreEntity,
                                    orderId = order.orderId
                                )
                            )
                        }
                        if (orderItems.isNotEmpty() && orderItemCoreEntities.isNotEmpty() && orderItems.size == orderItemCoreEntities.size ) {
                            saveInDB(orderCoreEntity, order, orderItems, orderItemCoreEntities)
                            response.message = "Order created successfully"
                        }
                       else{
                           response.message = "Order creation failed"
                       }
                    } else {
                        response.message = "Cart is empty"
                    }
                } else {
                    response.message = "User not found or cart price is invalid"
                }
            } else {
                response.message = "Username is empty"
            }
        } else {
            response.message = "Address is required"
        }

        response.responseCode = if (response.errors.isNotEmpty()) OC_BUGS else OC_OK
        return response
    }

    @Transactional
    fun saveInDB(
        orderCoreEntity: CoreEntity,
        order: Order,
        orderItems: List<OrderItem>,
        orderItemCoreEntities: List<CoreEntity>
    ) {
        coreEntityDao.save(orderCoreEntity)
        orderDao.save(order)
        coreEntityDao.saveAll(orderItemCoreEntities)
        orderItemService.orderItemDao.saveAll(orderItems)
    }
}