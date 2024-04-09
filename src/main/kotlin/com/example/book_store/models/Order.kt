package com.example.book_store.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime
import java.math.BigDecimal

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @Column(name = "order_id")
    val orderId:Long,
    @Column(name = "user_id")
    val userId:Long,
    @Column(name = "address")
    val address:String,
    @Column(name = "order_price")
    val orderPrice:BigDecimal,
    @Column(name = "order_date")
    val orderDate:DateTime,
    @Column(name = "order_code")
    val orderCode:String
)
