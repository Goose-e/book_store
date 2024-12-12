package com.example.book_store.repo

import com.example.book_store.dto.userDto.GetUserDto
import com.example.book_store.dto.userDto.GetUserDtoDB
import com.example.book_store.dto.userDto.UserDto
import com.example.book_store.models.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.login = :USERNAME")
    fun findByLogin(@Param("USERNAME") login: String?): User?

    @Query("SELECT NEW com.example.book_store.dto.userDto.GetUserDtoDB( u.login,u.login,u.userAge,u.userRole) FROM User u LEFT JOIN CoreEntity ent ON ent.coreEntityId = u.userId  ")
    fun getAllUserDto(): MutableCollection<GetUserDtoDB>
}
