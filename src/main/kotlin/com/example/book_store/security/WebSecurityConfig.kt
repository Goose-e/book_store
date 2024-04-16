package com.example.book_store.security

import com.example.book_store.jwt.JwtAuthEntryPoint
import com.example.book_store.jwt.JwtAuthTokenFilter
import com.example.book_store.models.enum.RoleEnum
import com.example.book_store.service.UserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(var userDetailsService: UserDetailsService? = null,val unauthorizedHandler: JwtAuthEntryPoint? = null
){
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationJwtTokenFilter(): JwtAuthTokenFilter {
        return JwtAuthTokenFilter()
    }
    @Throws(Exception::class)
    fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder())
    }
    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }



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