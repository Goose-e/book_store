package com.example.book_store.repo

import com.example.book_store.dto.orderDto.GetCartItemDB
import com.example.book_store.models.OrderItem
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface OrderItemRepository:CrudRepository<OrderItem,Long>
{
    @Query("select new com.example.book_store.dto.orderDto.GetCartItemDB(b.bookId,b.bookPrice,c.cartItemQuantity) from CartItem c join Book b on( b.bookId = c.bookId) join Cart car on (car.cartId = c.cartId) right join CoreEntity ent on (ent.coreEntityId = c.cartItemsId and ent.status != 5) where c.cartId = :id")
    fun getAllItems(id:Long?):MutableCollection<GetCartItemDB>
}