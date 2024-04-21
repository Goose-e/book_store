package com.example.book_store.repo

import com.example.book_store.models.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param


interface UserRepository : CrudRepository<User,Long> {
    @Query("select u from User u where u.login = :USERNAME")
    fun findByLogin(@Param("USERNAME") login: String?): User?


}
