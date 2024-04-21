package com.example.book_store.dao

import com.example.book_store.models.RoleRef
import com.example.book_store.models.enum.RoleEnum.values
import com.example.book_store.repo.RoleRepository
import org.springframework.stereotype.Component

@Component
class RoleRefDao(private val roleRefRepo: RoleRepository) {
    init {
        values().forEach { role ->
            roleRefRepo.save(RoleRef(role, role.getCode(), role.getName()))
        }
    }
}