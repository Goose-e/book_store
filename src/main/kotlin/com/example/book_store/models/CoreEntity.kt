package com.example.book_store.models

import com.example.book_store.models.enum.StatusRefEnum
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.util.Date

@Table("core_entities")
data class CoreEntity(
    @Id
    @Column("core_entity_id")
    val coreEntityId:Long,
    @Column("status_id")
    val statusId:StatusRefEnum,
    @Column("create_date")
    val createDate:Date,
    @Column("delete_date")
    val deleteDate:Date
)
