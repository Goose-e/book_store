package com.example.book_store.repo

import com.example.book_store.models.User
import jakarta.transaction.Transactional
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*


interface UserRepository:CrudRepository<User,Long>{
    fun existsUserByLogin(@Param("username") login: String?): Boolean
    fun findByLogin(@Param("username") login: String?): Optional<User?>?
    @Transactional
     fun deleteAllByLogin(@Param("username") username: String)
}
