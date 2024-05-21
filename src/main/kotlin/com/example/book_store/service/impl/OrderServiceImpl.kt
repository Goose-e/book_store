package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.BIGDECIMAL_ZERO
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.constant.SysConst.LONG_ZERO
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dao.OrderDao
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.cartItemDto.CreateOrderRequestDto
import com.example.book_store.dto.orderDto.CreateOrderItemList
import com.example.book_store.dto.orderDto.CreateOrderItemResponse
import com.example.book_store.dto.orderDto.GetCartItemDB
import com.example.book_store.map.OrderMapper
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.Order
import com.example.book_store.models.OrderItem
import com.example.book_store.models.enum.StatusEnum.ORDER_ACTUAL
import com.example.book_store.models.enum.StatusEnum.ORDER_ITEM_ACTUAL
import com.example.book_store.service.CoreEntityService
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
    val orderItemService: OrderItemService,
    val orderMapper: OrderMapper,
    val coreEntityService: CoreEntityService
) : OrderService {
    override fun createOrUpdateOrder(createOrderRequestDto: CreateOrderRequestDto): HttpResponseBody<CreateOrderItemList> {
        val response: HttpResponseBody<CreateOrderItemList> = CreateOrderItemResponse()
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name

        response.errors.addAll(validateCreateOrder(username, createOrderRequestDto, response).errors)

        if (response.haveNoErrors()) {
            val userId = orderDao.findUserId(username)
            val cart = orderDao.findCartByUserName(username)
            cart?.let {
                if (cart.cartPrice != BIGDECIMAL_ZERO) {
                    val orderCoreEntity = coreEntityService.createCoreEntity(ORDER_ACTUAL)

                    val order =
                        orderMapper.createOrderMapper(
                            createOrder(orderCoreEntity.coreEntityId),
                            createOrderRequestDto.address,
                            cart.cartPrice,
                            userId
                        )
                    val cartItems: MutableCollection<GetCartItemDB> =
                        orderItemService.orderItemDao.findAllItems(cart.cartId)
                    if (cartItems.isNotEmpty()) {
                        val orderItems = mutableListOf<OrderItem>()
                        val orderItemCoreEntities = mutableListOf<CoreEntity>()
                        cartItems.forEach { cartItem ->
                            val itemCoreEntity = coreEntityService.createCoreEntity(ORDER_ITEM_ACTUAL)
                            orderItemCoreEntities.add(itemCoreEntity)
                            orderItems.add(
                                orderItemService.orderItemMapper.mapOrderItemDtoToOrderItem(
                                    cartItem,
                                    itemCoreEntity,
                                    orderId = order.orderId
                                )
                            )
                        }
                        saveInDB(orderCoreEntity, order, orderItems, orderItemCoreEntities)
                    } else {
                        response.message = "Cart is empty"
                    }
                } else {
                    response.message = "Cart price is invalid"
                }
            } ?: run {
                response.message = "Cart is not found"
            }
        }

        response.responseCode = if (response.errors.isNotEmpty()) OC_BUGS else OC_OK
        if (response.errors.isEmpty()) {
            response.message = "Order created successfully"
        } else {
            response.message = "Order creation failed"
        }
        return response
    }

    private fun validateCreateOrder(
        username: String?,
        createOrderRequestDto: CreateOrderRequestDto,
        response: HttpResponseBody<CreateOrderItemList>
    ): HttpResponseBody<CreateOrderItemList> {
        if (createOrderRequestDto.address == EMPTY_STRING) {
            response.message = "Address is required"
        }
        if (username.isNullOrBlank()) {
            response.message = "Username is empty"
        }
        response.responseCode = if (response.errors.isNotEmpty()) OC_BUGS else OC_OK
        return response
    }

    private fun createOrder(entityId: Long): Order = Order(
        orderId = entityId,
        userId = LONG_ZERO,
        address = EMPTY_STRING,
        orderPrice = BIGDECIMAL_ZERO,
        orderDate = now(),
        orderCode = GenerationService.generateCode()
    )

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