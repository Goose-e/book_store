package com.example.book_store.dao

import com.example.book_store.models.StatusRef
import com.example.book_store.models.enum.StatusEnum.values
import com.example.book_store.repo.StatusRefRepository
import org.springframework.stereotype.Component

@Component
class StatusRefDao(private val statusRefRepo: StatusRefRepository) {
    init {
        values().forEach { status ->
            statusRefRepo.save(StatusRef(status, status.getCode(), status.getName()))
        }
    }
}