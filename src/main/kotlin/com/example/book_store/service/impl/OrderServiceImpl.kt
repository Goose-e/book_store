package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.BIGDECIMAL_ZERO
import com.example.book_store.constant.SysConst.EMPTY_STRING
import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
import com.example.book_store.constant.SysConst.LONG_ZERO
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.BookDao
import com.example.book_store.dao.CartItemDao
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dao.OrderDao
import com.example.book_store.dto.HttpResponseBody
import com.example.book_store.dto.bookDto.ChangeBookStatusDto
import com.example.book_store.dto.cartItemDto.CreateOrderRequestDto
import com.example.book_store.dto.orderDto.*
import com.example.book_store.map.BookMapper
import com.example.book_store.map.CartItemMapper
import com.example.book_store.map.OrderMapper
import com.example.book_store.models.Book
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.Order
import com.example.book_store.models.OrderItem
import com.example.book_store.models.enum.StatusEnum
import com.example.book_store.models.enum.StatusEnum.*
import com.example.book_store.service.*
import org.dbs.validator.ErrorInfo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime.now
import java.util.*

@Service
class OrderServiceImpl(
    val orderDao: OrderDao,
    val coreEntityDao: CoreEntityDao,
    val orderItemService: OrderItemService,
    val orderMapper: OrderMapper,
    val coreEntityService: CoreEntityService,
    val cartItemMapper: CartItemMapper,
    val cartItemDao: CartItemDao,
    val bookMapper: BookMapper,
    val bookDao: BookDao,
    val bookService: BookService
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
                    val order = orderMapper.createOrderMapper(
                        createOrder(orderCoreEntity.coreEntityId),
                        createOrderRequestDto.address,
                        cart.cartPrice,
                        userId
                    )
                    val cartItems: MutableCollection<GetCartItemDB> =
                        orderItemService.orderItemDao.findAllItems(cart.cartId)
                    if (cartItems.isNotEmpty()) {
                        val booksInCart: MutableCollection<Book> = cartItemDao.findAllBooksInCart(cart.cartId)
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
                            val ent = cartItemDao.findEntityByItemCode(cartItem.cartItemCode)
                            if (ent != null) {
                                coreEntityDao.save(cartItemMapper.deleteCartItem(ent))
                            }
                            booksInCart.firstOrNull { book -> book.bookId == cartItem.bookId }?.let { book ->
                                val quantity = book.bookQuantity - cartItem.orderItemAmount
                                bookDao.save(bookService.bookMapper.mapChangeBookQuantity(book, quantity))
                                if (quantity == 0) {
                                    val entityToDelete = bookDao.findByCodeForDel(book.bookCode)
                                    val bookStatus = StatusEnum.getEnum(2)
                                    if (entityToDelete != null) {
                                        coreEntityDao.save(BookMapper.mapDeleteEntToEnt(entityToDelete, bookStatus))
                                    }

                                }
                            }
                        }
                        saveInDB(orderCoreEntity, order, orderItems, orderItemCoreEntities, booksInCart)
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

    override fun getAllOrders(): HttpResponseBody<ListAllOrders> {
        val response: HttpResponseBody<ListAllOrders> = GetAllOrdersResponse()


        val ordersList: MutableCollection<GetAllOrdersDB> = orderDao.getAllOrders()
        if (ordersList.isNotEmpty()) {
            val getOrderDto = ordersList.map {
                orderMapper.getOrdersAllMapper(it)
            }

            response.responseEntity = ListAllOrders(listOrderDto = getOrderDto)
            response.message = "Orders"
        } else {
            response.message = "Orders is empty"
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Orders is empty"))
        }

        if (response.errors.isNotEmpty()) {
            response.responseCode = OC_BUGS
        } else {
            response.responseCode = OC_OK
        }

        return response
    }

    fun convertImageToBase64(image: ByteArray?): String? {
        return image?.let { Base64.getEncoder().encodeToString(it) }
    }

    override fun changeOrderStatus(orderDetailRequestDto: OrderChangeStatusRequest): HttpResponseBody<NewOrderStatus> {
        val response: HttpResponseBody<NewOrderStatus> = OrderChangeStatus()
        val orderStatusCode: String
        lateinit var changedOrder: NewOrderStatus
        println(orderDetailRequestDto.orderCode)
        orderDetailRequestDto.orderCode.let { code ->
            orderDao.getOrderByOrderEntCodeForChangeStatus(code)?.let { ent ->
                val newStatus: StatusEnum = if (ent.status == StatusEnum.getEnumByCode("ORDER_ACTUAL")) {
                    ORDER_CLOSED
                } else {
                    ORDER_ACTUAL
                }
                orderStatusCode = newStatus.getCode()
                coreEntityDao.save(OrderMapper.mapDeleteEntToEnt(ent.coreEntity, newStatus))
                changedOrder = OrderMapper.changeOrderDto(orderStatusCode)
                response.responseEntity = changedOrder
                response.message = "Order Status Changed Successfully "

            }
                ?: run {
                    response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Book not found"))
                    response.message = "Book not found"
                }
        }
        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }

    override fun getOrderDetails(orderDetailRequestDto: GetOrderDetailRequestDto): HttpResponseBody<ListOrderDetail> {
        val response: HttpResponseBody<ListOrderDetail> = GetOrderListResponse()
        var price: BigDecimal = BigDecimal.ZERO
        orderDetailRequestDto.orderCode.let { orderCode ->
            val orderItems: MutableCollection<GetOrderItemListDB> = orderDao.findOrderByOrderCode(orderCode)
            if (orderItems.isNotEmpty()) {
                val listBookDto = orderItems.map {
                    price += it.bookPrice * BigDecimal.valueOf(it.itemQuantity.toLong())
                    orderMapper.mapBookFromListToBookDTO(it, convertImageToBase64(it.image))
                }
                val orderStatus: StatusEnum = orderDao.getOrderStatusByCode(orderCode)
                response.responseEntity =
                    ListOrderDetail(listBookDto = listBookDto, price = price, orderStatus = orderStatus.getCode())
                response.message = "Books"
            } else {
                response.message = "Order is empty"
                response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Order is empty"))
            }

            if (response.errors.isNotEmpty()) {
                response.responseCode = OC_BUGS
            } else {
                response.responseCode = OC_OK
            }
        }
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
        orderItemCoreEntities: List<CoreEntity>,
        books: MutableCollection<Book>,
    ) {
        coreEntityDao.save(orderCoreEntity)
        orderDao.save(order)
        coreEntityDao.saveAll(orderItemCoreEntities)
        orderItemService.orderItemDao.saveAll(orderItems)
        // bookDao.saveAll(books)
    }

    override fun getAllOrdersOfUser(): HttpResponseBody<ListOrders> {
        val response: HttpResponseBody<ListOrders> = GetOrdersResponse()
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name
        username?.let {
            val ordersList: MutableCollection<GetOrdersDB> = orderDao.findOrderByUserName(username)
            if (ordersList.isNotEmpty()) {
                val getOrderDto = ordersList.map {
                    orderMapper.getOrdersMapper(it)
                }

                response.responseEntity = ListOrders(listOrderDto = getOrderDto)
                response.message = "Orders"
            } else {
                response.message = "Orders is empty"
                response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Orders is empty"))
            }

            if (response.errors.isNotEmpty()) {
                response.responseCode = OC_BUGS
            } else {
                response.responseCode = OC_OK
            }
        }
        return response
    }
}