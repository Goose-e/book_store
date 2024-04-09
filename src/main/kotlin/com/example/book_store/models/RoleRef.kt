package com.example.book_store.models

import com.example.book_store.models.enum.RoleEnum
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
@Entity
@Table("roles_ref")
data class RoleRef(
    @Id
    @Column("role_id")
    val roleId:RoleEnum,
    @Column("role_code")
    val roleCode:String,
    @Column("role_name")
    val roleName:String
)
