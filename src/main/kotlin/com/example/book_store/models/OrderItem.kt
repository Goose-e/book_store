package com.example.book_store.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @Column(name = "order_item_id")
    val orderItemId:Long,
    @Column(name = "book_id")
    val bookId:Long,
    @Column(name = "order_id")
    val orderId:Long,
    @Column(name = "order_item_code")
    val orderItemCode:String,
    @Column(name = "order_item_price")
    val orderItemPrice:BigDecimal,
    @Column(name = "order_item_amount")
    val orderItemAmount:Int,
    @Column(name = "order_item_total_price")
    val orderItemTotalPrice:BigDecimal

)
