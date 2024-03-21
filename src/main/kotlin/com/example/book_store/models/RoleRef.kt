package com.example.book_store.models

import com.example.book_store.models.enum.RoleRefEnum
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

@Table("roles_ref")
data class RoleRef(
    @Id
    @Column("role_id")
    val roleRefId:RoleRefEnum,
    @Column("role_code")
    val roleRefCode:String,
    @Column("role_name")
    val roleRefName:String
)
