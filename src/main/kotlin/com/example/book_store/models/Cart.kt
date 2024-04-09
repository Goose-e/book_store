package com.example.book_store.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Entity
@Table("carts")
data class Cart(
    @Id
    @Column("cart_id")
    val cartId:Long,
    @Column("user_id")
    val userId:Long,
    @Column("cart_code")
    val cartCode:String
)
