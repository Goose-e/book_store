package com.example.book_store.config.security

import com.example.book_store.jwt.JwtAuthEntryPoint
import com.example.book_store.jwt.JwtAuthTokenFilter
import com.example.book_store.jwt.JwtProvider
import com.example.book_store.models.enum.RoleEnum
import com.example.book_store.service.UserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val unauthorizedHandler: JwtAuthEntryPoint,
    val tokenProvider: JwtProvider
) {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationJwtTokenFilter(): JwtAuthTokenFilter {
        return JwtAuthTokenFilter(tokenProvider, userDetailsService)
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun web(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { } // ← добавь сюда
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/api/v1/auth/**", permitAll)
                authorize("/api/v1/userManagement/**", hasAuthority(RoleEnum.ADMIN.name))
                authorize("/api/v1/users/**", authenticated)
                authorize("/api/v1/cart/**", authenticated)
                authorize("/api/v1/order/**", authenticated)
                authorize("/api/v1/bookstore/**", permitAll)
                authorize("/api/v1/bookstore/admin", hasAuthority(RoleEnum.ADMIN.name))
                authorize(anyRequest, denyAll)
            }
            exceptionHandling {
                // authenticationEntryPoint(unauthorizedHandler)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(authenticationJwtTokenFilter())
        }
        return http.build()
    }
}