package com.example.book_store.models

import com.example.book_store.models.enum.StatusesRefEnum
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

@Table("statuses_ref")
data class StatusesRef(
    @Id
    @Column("status_id")
    val statusId:StatusesRefEnum,
    @Column("status_code")
    val statusCode:String,
    @Column("status_name")
    val statusName:String
)
