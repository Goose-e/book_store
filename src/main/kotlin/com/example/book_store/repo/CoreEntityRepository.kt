package com.example.book_store.repo

import com.example.book_store.models.CoreEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CoreEntityRepository : CrudRepository<CoreEntity, Long> {
    @Query("select ce from CoreEntity ce where ce.coreEntityId = :id")
    fun findEntityById(@Param("id") entityId: Long?): CoreEntity?
}