package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.LOCALDATETIME_NULL
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dao.OrderItemDao
import com.example.book_store.dto.orderDto.CreateOrderItemList
import com.example.book_store.dto.orderDto.CreateOrderItemRequestDto
import com.example.book_store.dto.orderDto.GetCartItemDB
import com.example.book_store.map.OrderItemMapper
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.OrderItem
import com.example.book_store.models.enum.StatusEnum.ORDER_ITEM_ACTUAL
import com.example.book_store.service.GenerationService.Companion.generateEntityId
import com.example.book_store.service.OrderItemService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime.now

@Service
class OrderItemServiceImpl(
    private val orderItemDao: OrderItemDao,
    val coreEntityDao: CoreEntityDao,
    private val orderItemMapper: OrderItemMapper
) : OrderItemService {
    override fun createOrderItem(orderItemDto: CreateOrderItemRequestDto): CreateOrderItemList? {
        val response: CreateOrderItemList?
        val cartId = orderItemDao.findCartByUserName(orderItemDto.login)
        val getBook: MutableCollection<GetCartItemDB> = orderItemDao.findAllItems(cartId)
        var orderItem: OrderItem
        var bookName: String
        if (getBook.isNotEmpty()) {
            getBook.map {
                val coreEntity = CoreEntity(
                    coreEntityId = generateEntityId(),
                    createDate = now(),
                    deleteDate = LOCALDATETIME_NULL,
                    status = ORDER_ITEM_ACTUAL
                )
                orderItem = orderItemMapper.mapCartItemToOrderItem(it, orderItemDto.orderId, coreEntity)
                saveToDB(coreEntity, orderItem)
            }
            val createOrderItem = getBook.map {
                bookName = orderItemDao.findBookNameByBookId(it.bookId)
                orderItemMapper.mapCartItemListToOrderItemList(it, bookName)
            }
            response = CreateOrderItemList(createOrderItem)

        } else {
           response = null
        }
        return response
    }

    @Transactional
    fun saveToDB(coreEntity: CoreEntity, order: OrderItem) {
        coreEntityDao.save(coreEntity)
        orderItemDao.save(order)

    }


}