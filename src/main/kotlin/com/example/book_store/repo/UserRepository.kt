package com.example.book_store.repo

import com.example.book_store.models.User
import com.example.book_store.models.enum.RoleEnum
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository:CrudRepository<User,Int>{
//    private val users = listOf(
//        User(userId = UUID.randomUUID(), login = "Article 1", password =  "Content 1",username = "dada",userAge = 12, userRoleId = RoleEnum.USER),
//    )
}
