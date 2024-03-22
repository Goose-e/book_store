package com.example.book_store.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import java.util.*

class AccessTokenAuthentication (
    val userId: Long,
    val userPrefLang: Locale,
    authorities: Set<String>,
    ): AbstractAuthenticationToken(authorities.map { GrantedAuthority { it } }){
        override fun getCredentials(): Any {
            return ""
        }

        override fun getPrincipal(): Any {
            return userId
        }
}