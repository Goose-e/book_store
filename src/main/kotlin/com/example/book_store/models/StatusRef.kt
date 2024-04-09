package com.example.book_store.models

import com.example.book_store.models.enum.StatusEnum
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
@Entity
@Table("statuses_ref")
data class StatusRef(
    @Id
    @Column("status_id")
    val statusId:StatusEnum,
    @Column("status_code")
    val statusCode:String,
    @Column("status_name")
    val statusName:String
)
