package com.example.book_store.models

import com.example.book_store.models.enum.StatusEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "core_entities")
data class CoreEntity(
    @Id
    @Column(name = "core_entity_id")
    val coreEntityId:Long,
    @Column(name = "status_id")
    val statusId:StatusEnum,
    @Column(name = "create_date")
    val createDate:Date,
    @Column(name = "delete_date")
    val deleteDate:Date
)
