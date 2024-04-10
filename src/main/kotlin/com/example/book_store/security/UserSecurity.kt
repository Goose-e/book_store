package com.example.book_store.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserSecurity
        (
       private val id: Long,
       private val login: String,
        private val password: String,
        private val username:String,
        private val uAuthorities: MutableCollection<GrantedAuthority>
    ) : UserDetails {
        override fun getAuthorities() = uAuthorities
        override fun getPassword() = password
        override fun getUsername() = username
        fun getLogin() = login
        override fun isAccountNonExpired() = true
        override fun isAccountNonLocked() = true
        override fun isCredentialsNonExpired()= true
        override fun isEnabled() = true
    }