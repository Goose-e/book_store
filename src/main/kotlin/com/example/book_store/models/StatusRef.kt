package com.example.book_store.models

import com.example.book_store.models.enum.StatusEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "statuses_ref")
data class StatusRef(
    @Id
    @Column(name ="status_id")
    val statusId: StatusEnum,
    @Column(name = "status_code")
    val statusCode:String,
    @Column(name ="status_name")
    val statusName:String
)
