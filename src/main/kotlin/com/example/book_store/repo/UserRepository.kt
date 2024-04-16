package com.example.book_store.repo

import com.example.book_store.models.User
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.util.*


interface UserRepository:CrudRepository<User,Long>{
    fun existsUserByLogin(login: String?): Boolean
    fun findByLogin(login: String?): Optional<User?>?
    @Query("select u from User u where u.login = ?1")
    fun findByLoginWithinOpt(login: String?): User?
    fun existsByLogin(login: String?): Boolean?
    fun findAllByStatus(statusId: Long?): List<User?>?

}
