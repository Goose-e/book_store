package com.example.book_store.service

import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.StatusEnum

interface CoreEntityService {
    fun createCoreEntity(entityStatus: StatusEnum): CoreEntity
}