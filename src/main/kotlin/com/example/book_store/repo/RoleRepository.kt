package com.example.book_store.repo

import com.example.book_store.models.RoleRef
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<RoleRef, Long>