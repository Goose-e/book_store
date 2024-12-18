package com.example.book_store.service

import com.example.book_store.exceptions.UserNotFoundException
import com.example.book_store.repo.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailsService(
    private val repository: UserRepository
) : UserDetailsService {


    @Throws(UserNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByLoginForReg(username)
            ?: throw UserNotFoundException("User '$username' not found")

        val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority(user.userRole.getName()))

        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password(user.password)
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }

}
