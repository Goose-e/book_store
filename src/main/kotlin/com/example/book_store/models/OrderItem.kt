package com.example.book_store.models

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.math.BigDecimal

@Table("order_items")
data class OrderItem(
    @Id
    @Column("order_item_id")
    val orderItemId:Long,
    @Column("book_id")
    val bookId:Long,
    @Column("order_id")
    val orderId:Long,
    @Column("order_item_code")
    val orderItemCode:String,
    @Column("order_item_price")
    val orderItemPrice:BigDecimal,
    @Column("order_item_amount")
    val orderItemAmount:Int,
    @Column("order_item_total_price")
    val orderItemTotalPrice:BigDecimal

)
