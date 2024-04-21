package com.example.book_store.jwt

import com.example.book_store.service.UserDetailsService
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthTokenFilter (

) : OncePerRequestFilter() {

    private lateinit var tokenProvider: JwtProvider
    private lateinit var userDetailsService: UserDetailsService
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJwt(request)
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                val username = tokenProvider.getUserNameFromJwtToken(jwt)
                val userDetails = userDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )

                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication

            }
        } catch (ex: IllegalArgumentException) {
            logger.error("Invalid JWT token: {}", ex)
        } catch (ex: JwtException) {
            logger.error("JWT token validation failed: {}", ex)
        }

        filterChain.doFilter(request, response)
    }


    private fun getJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.replace("Bearer ", "")
        } else null
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtAuthTokenFilter::class.java)
    }
}