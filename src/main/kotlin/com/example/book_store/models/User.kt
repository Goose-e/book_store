package com.example.book_store.models

import com.example.book_store.models.enum.RoleEnum
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Entity
@Table("users")
data class User(
    @Id
    @Column("user_id")
    val userId:Long? = null,
    @Column("login")
    val login:String,
    @Column("password")
    val password:String,
    @Column("username")
    val username:String,
    @Column("user_age")
    val userAge:Int,
    @Column("user_role_id")
    val userRoleId:RoleEnum
)
