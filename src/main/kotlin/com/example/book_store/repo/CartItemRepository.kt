package com.example.book_store.repo

import com.example.book_store.dto.cartItemDto.GetItemListDtoDB
import com.example.book_store.models.Book
import com.example.book_store.models.CartItem
import com.example.book_store.models.CoreEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CartItemRepository : CrudRepository<CartItem, Int> {
    @Query("select b.bookId from Book b where b.bookCode = :code")
    fun findBookByBookCode(@Param("code") bookCode: String?): Long?

    @Query("SELECT c.cartId FROM Cart c JOIN User u ON c.userId = u.userId  WHERE u.login = :login")
    fun findCartIdByUserLogin(@Param("login") login: String): Long?

    @Query("SELECT c FROM CartItem c right join CoreEntity ent on (ent.coreEntityId = c.cartItemsId and ent.status != 5) where c.bookId = (select b.bookId from Book b where b.bookCode = :bookCode  ) and  c.cartId =:cartId ") // AND c.cartItemQuantity <= b.bookQuantity+1
    fun findItemInCartByBookCode(@Param("bookCode") bookCode: String,@Param("cartId") cartId:Long?): CartItem?

    @Query("SELECT ent FROM CartItem c JOIN CoreEntity ent on  ent.coreEntityId = c.cartItemsId  where c.cartItemsCode = :code")
    fun findEntityByItemCode(@Param("code") bookCode: String): CoreEntity?

    @Query("SELECT ci FROM CartItem ci where ci.cartItemsCode = :code")
    fun findItemByItemCode(@Param("code") code: String): CartItem?

    @Query("SELECT ci FROM CartItem ci JOIN Book b ON ci.bookId = b.bookId WHERE  ci.cartItemsCode = :code AND :quantity <= b.bookQuantity ")
    fun findItemCheck(@Param("code") code: String,@Param("quantity") quantity:Int ): CartItem?

    @Query("select new com.example.book_store.dto.cartItemDto.GetItemListDtoDB(b.bookName, b.genre, b.bookCode, b.bookPrice, c.cartItemsCode, c.cartItemQuantity,b.image) from CartItem c join Book b on( b.bookId = c.bookId) join Cart car on (car.cartId = c.cartId) right join CoreEntity ent on (ent.coreEntityId = c.cartItemsId and ent.status != 5) where c.cartId = :id")
    fun getAllItems(@Param("id") id: Long?): MutableCollection<GetItemListDtoDB>
    @Query("select b from CartItem c join Book b on( b.bookId = c.bookId) join Cart car on (car.cartId = c.cartId) right join CoreEntity ent on (ent.coreEntityId = c.cartItemsId and ent.status != 5) where c.cartId = :id")
    fun findAllBooksInCart(@Param("id") id: Long?): MutableCollection<Book>
}