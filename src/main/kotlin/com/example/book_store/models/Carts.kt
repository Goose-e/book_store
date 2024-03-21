package com.example.book_store.models

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

@Table("carts")
data class Carts(
    @Id
    @Column("cart_id")
    val cartId:Long,
    @Column("user_id")
    val userId:Long,
    @Column("cart_code")
    val cartCode:String
)
