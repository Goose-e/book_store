package com.example.book_store.models
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

@Table("cart_items")
data class CartItem(
    @Id
    @Column("cart_items_id")
    val cartItemsId:Long,
    @Column("book_id")
    val bookId:Long,
    @Column("cart_id")
    val cartId:Long,
    @Column("cart_item_code")
    val cartItemsCode:String
)
