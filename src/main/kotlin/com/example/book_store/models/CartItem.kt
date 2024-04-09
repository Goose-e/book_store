package com.example.book_store.models
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Entity
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
