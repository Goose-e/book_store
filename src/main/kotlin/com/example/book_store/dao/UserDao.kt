package com.example.book_store.dao

import com.example.book_store.models.User
import com.example.book_store.repo.UserRepository


class UserDao(private val userRepo:UserRepository) {

    fun findByLogin(login: String?): User {
        return userRepo.findByLoginWithinOpt(login)
    }

    fun findAll(): List<User> {
        return userRepo.findAllByStatus(EStatus.ACTIVE.getId())
    }
}