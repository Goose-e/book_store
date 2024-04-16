package com.example.book_store.repo

import com.example.book_store.models.RoleRef
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface RoleRepository:CrudRepository<RoleRef,Long> {
    fun findByName(@Param("name") name: String): RoleRef
}