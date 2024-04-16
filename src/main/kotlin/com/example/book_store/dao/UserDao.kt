package com.example.book_store.dao

import com.example.book_store.models.User
import com.example.book_store.repo.UserRepository
import org.springframework.stereotype.Component

@Component
class UserDao(private val userRepo:UserRepository
    ) {

    fun findByLogin(login: String?): User? = userRepo.findByLoginWithinOpt(login)
    fun findAll() = userRepo.findAll()

}