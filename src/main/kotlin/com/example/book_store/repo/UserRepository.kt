package com.example.book_store.repo

import com.example.book_store.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository:CrudRepository<User,Int>{

}
