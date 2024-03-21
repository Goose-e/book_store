package com.example.book_store.repo

import com.example.book_store.models.CoreEntity
import org.springframework.data.repository.CrudRepository

interface CoreEntityRepository:CrudRepository<CoreEntity,Int> {
}