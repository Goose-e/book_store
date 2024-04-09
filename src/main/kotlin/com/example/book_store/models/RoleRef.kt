package com.example.book_store.models

import com.example.book_store.models.enum.RoleEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles_ref")
data class RoleRef(
    @Id
    @Column(name = "role_id")
    val roleId:RoleEnum,
    @Column(name = "role_code")
    val roleCode:String,
    @Column(name = "role_name")
    val roleName:String
)
