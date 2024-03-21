package com.example.book_store.models
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
@Table("users")
data class Users(
    @Id
    @Column("user_id")
    val userId:Long,
    @Column("login")
    val login:String,
    @Column("password")
    val password:String,
    @Column("username")
    val username:String,
    @Column("user_age")
    val userAge:Int,
    @Column("user_role_id")
    val userRoleId:Long
)
