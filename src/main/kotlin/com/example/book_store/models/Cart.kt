package com.example.book_store.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "carts")
data class Cart(
    @Id
    @Column(name = "cart_id")
    val cartId:Long?,

    @Column(name = "user_id")
    val userId:Long?,
    @Column(name = "cart_code")
    val cartCode:String
)
