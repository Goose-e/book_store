package com.example.book_store.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import com.example.book_store.models.enum.RoleEnum
@Configuration
class SpringSecurityConfig {
    @Bean
    fun web(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/api/v1/auth/**", permitAll)
                authorize("/api/v1/user-management/**", hasAuthority(RoleEnum.ADMIN.getName()))
                authorize("/api/v1/users/**", authenticated)

                authorize(anyRequest, denyAll)
            }
            oauth2ResourceServer {
                jwt {
                    jwtAuthenticationConverter = AccessTokenAuthenticationOAuth2Converter()
                }
            }
        }

        return http.build()
    }
}