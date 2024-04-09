package com.example.book_store.models

import com.example.book_store.models.enum.StatusEnum
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Entity
@Table("core_entities")
data class CoreEntity(
    @Id
    @Column("core_entity_id")
    val coreEntityId:Long,
    @Column("status_id")
    val statusId:StatusEnum,
    @Column("create_date")
    val createDate:Date,
    @Column("delete_date")
    val deleteDate:Date
)
