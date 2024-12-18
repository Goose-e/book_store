package com.example.book_store.repo

import com.example.book_store.dto.userDto.GetUserDtoDB
import com.example.book_store.dto.userDto.NewStatusDB
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN CoreEntity ce ON ce.coreEntityId = u.userId WHERE u.login = :USERNAME  ")
    fun findByLoginForReg(@Param("USERNAME") login: String): User?

    @Query("SELECT new com.example.book_store.dto.userDto.NewStatusDB(u.login,ce.status)  FROM User u  JOIN CoreEntity ce ON ce.coreEntityId=u.userId WHERE u.login = :USERNAME")
    fun findByLogin(@Param("USERNAME") login: String): NewStatusDB?

    @Query("SELECT ce FROM User u JOIN CoreEntity ce ON ce.coreEntityId=u.userId WHERE u.login = :USERNAME ")
    fun findByEntLogin(@Param("USERNAME") login: String): CoreEntity?

    @Query("SELECT NEW com.example.book_store.dto.userDto.GetUserDtoDB( u.login,u.login,u.userRole,ent.status) FROM User u JOIN CoreEntity ent ON ent.coreEntityId = u.userId  ")
    fun getAllUserDto(): MutableCollection<GetUserDtoDB>
}
