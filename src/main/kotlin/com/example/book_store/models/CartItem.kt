package com.example.book_store.models
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id
    @Column(name = "cart_items_id")
    val cartItemsId:Long,
    @Column(name = "book_id")
    val bookId:Long,
    @Column(name = "cart_id")
    val cartId:Long,
    @Column(name = "cart_item_code")
    val cartItemsCode:String
)
