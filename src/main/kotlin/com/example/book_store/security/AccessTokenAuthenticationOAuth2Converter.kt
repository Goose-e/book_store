package com.example.book_store.security

import org.springframework.core.convert.converter.Converter
import java.util.*
import org.springframework.security.oauth2.jwt.Jwt
class AccessTokenAuthenticationOAuth2Converter: Converter<Jwt, AccessTokenAuthentication> {
    override fun convert(source: Jwt): AccessTokenAuthentication {
        val userId = source.getClaim<Long>("userId")
        val role = source.getClaim<String>("role")
        val prefLang = source.getClaim<String>("prefLang")

        if (userId == null || role == null || prefLang == null) {
            throw IllegalArgumentException("Can't find all required claims in accessToken. Is userId present: ${userId != null}. Is role present: ${role != null}. Is prefLang present: ${prefLang != null}")
        }

        return AccessTokenAuthentication(
            userId = userId,
            userPrefLang = Locale(prefLang),
            authorities = setOf(role)
        ).apply {
            isAuthenticated = true
        }
    }
}