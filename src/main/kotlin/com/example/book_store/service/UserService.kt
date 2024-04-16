package com.example.book_store.service

import com.example.book_store.dao.UserDao
import com.example.book_store.dto.UserDto
import com.example.book_store.models.User
import com.example.book_store.repo.UserRepository
import com.example.book_store.service.impl.IUserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserService (
    private val repository: UserRepository,
    private val userDao:UserDao
): IUserService {


    override fun getAllUsers(): List<User?>? {
        return userDao.findAll()
    }

    override fun loadUserByUsername(login: String): UserDetails {

        val user: User = repository
            .findByLogin(login)
            .orElseThrow { UsernameNotFoundException("User not found with  username: $login") }
        return IUserService.build(user)
    }



    override fun getUserById(userId: Long?): ResponseEntity<UserDto?>? {
        TODO("Not yet implemented")
    }

    override fun updateUser(userId: Long?, userDetails: UserDto?): ResponseEntity<UserDto?>? {
        TODO("Not yet implemented")
    }

}
//front на чём-то(возможно view js(angular js))