package com.example.book_store.models

import com.example.book_store.models.enum.StatusRefEnum
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

@Table("statuses_ref")
data class StatusRef(
    @Id
    @Column("status_id")
    val statusId:StatusRefEnum,
    @Column("status_code")
    val statusCode:String,
    @Column("status_name")
    val statusName:String
)
