package com.example.book_store.repo

import com.example.book_store.dto.orderDto.GetAllOrdersDB
import com.example.book_store.dto.orderDto.GetOrderItemListDB
import com.example.book_store.dto.orderDto.GetOrdersDB
import com.example.book_store.dto.orderDto.OrderChangeStatusDB
import com.example.book_store.models.Cart
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.Order
import com.example.book_store.models.enum.StatusEnum
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface OrderRepository : CrudRepository<Order, Long> {
    @Query("SELECT c.cartPrice FROM Cart c JOIN User u ON c.userId = u.userId  WHERE u.login = :login")
    fun findCartPriceByUserName(@Param("login") login: String): BigDecimal?

    @Query("SELECT new com.example.book_store.dto.orderDto.GetAllOrdersDB(u.login ,o.address, o.orderPrice,o.orderDate,o.orderCode,ce.status ) FROM Order o JOIN User u ON o.userId = u.userId JOIN CoreEntity ce  ON ce.coreEntityId = o.orderId ")
    fun getAllOrders(): MutableCollection<GetAllOrdersDB>

    @Query("SELECT c FROM Cart c JOIN User u ON c.userId = u.userId  WHERE u.login = :login")
    fun findCartByUserName(@Param("login") login: String): Cart?

    @Query("SELECT u.userId FROM User u where u.login = :login")
    fun findUserId(@Param("login") login: String): Long

    @Query("SELECT new com.example.book_store.dto.orderDto.GetOrdersDB( o.address,o.orderPrice,o.orderDate,o.orderCode,ce.status) FROM  User u  JOIN Order o  on u.userId =  o.userId join CoreEntity ce on ce.coreEntityId = o.orderId where u.login = :login")
    fun findOrderByUserName(@Param("login") login: String): MutableCollection<GetOrdersDB>

    @Query("select new com.example.book_store.dto.orderDto.GetOrderItemListDB(b.bookName,b.bookCode,b.bookPrice,oi.orderItemAmount,b.image,b.genre) FROM Order o JOIN OrderItem oi ON o.orderId = oi.orderId JOIN Book b ON oi.bookId = b.bookId JOIN CoreEntity ce ON o.orderId = ce.coreEntityId where o.orderCode = :code")
    fun getOrderByOrderCode(@Param("code") code: String): MutableCollection<GetOrderItemListDB>

    @Query("SELECT ent.status FROM Order o JOIN CoreEntity ent ON o.orderId = ent.coreEntityId where o.orderCode = :code")
    fun getOrderStatusByCode(@Param("code") code: String): StatusEnum

    @Query("SELECT new com.example.book_store.dto.orderDto.OrderChangeStatusDB(ce,ce.status) FROM Order o JOIN CoreEntity ce ON o.orderId = ce.coreEntityId where o.orderCode = :code")
    fun getOrderByOrderCodeForChangeStatus(@Param("code") code: String): OrderChangeStatusDB?


}