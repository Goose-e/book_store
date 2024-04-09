package com.example.book_store.models

import com.example.book_store.models.enum.RoleEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "user_id")
    val userId:Long? = null,
    @Column(name = "login")
    val login:String,
    @Column(name = "password")
    val password:String,
    @Column(name = "username")
    val username:String,
    @Column(name = "user_age")
    val userAge:Int,
    @Column(name = "user_role_id")
    val userRoleId:RoleEnum
)
