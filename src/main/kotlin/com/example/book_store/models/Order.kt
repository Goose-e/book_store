package com.example.book_store.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Entity
@Table("orders")
data class Order(
    @Id
    @Column("order_id")
    val orderId:Long,
    @Column("user_id")
    val userId:Long,
    @Column("address")
    val address:String,
    @Column("order_price")
    val orderPrice:BigDecimal,
    @Column("order_date")
    val orderDate:DateTime,
    @Column("order_code")
    val orderCode:String
)
