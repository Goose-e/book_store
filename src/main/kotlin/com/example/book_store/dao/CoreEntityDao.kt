package com.example.book_store.dao

import com.example.book_store.models.CoreEntity
import com.example.book_store.repo.CoreEntityRepository
import org.springframework.stereotype.Component

@Component
class CoreEntityDao(private val coreEntityRepo: CoreEntityRepository) {
    fun save(coreEntity: CoreEntity) = coreEntityRepo.save(coreEntity)
    fun findEntityById(id: Long?): CoreEntity? = coreEntityRepo.findEntityById(id)
}