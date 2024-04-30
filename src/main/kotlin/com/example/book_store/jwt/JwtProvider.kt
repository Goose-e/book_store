package com.example.book_store.jwt

import com.example.book_store.repo.UserRepository
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys.secretKeyFor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

@Component
class JwtProvider(
    var userRepository: UserRepository

) {

    private val logger: Logger = LoggerFactory.getLogger(JwtProvider::class.java)



    @Value("\${assm.app.jwtSecret}")
    lateinit var jwtSecret: String

    @Value("\${assm.app.jwtExpiration}")
    var jwtExpiration: Int? = 0

    fun generateJwtToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date((Date()).time + jwtExpiration!! * 1000))
            .signWith(SignatureAlgorithm.HS512, secretKeyFor(SignatureAlgorithm.HS512))
            .compact()
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKeyFor(SignatureAlgorithm.HS512)).build().parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature -> Message: {} ", e)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token -> Message: {}", e)
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token -> Message: {}", e)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token -> Message: {}", e)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty -> Message: {}", e)
        }

        return false
    }

    fun getUserNameFromJwtToken(token: String): String {
        return Jwts.parser()
            .verifyWith(secretKeyFor(SignatureAlgorithm.HS512))
            .build()
            .parseClaimsJws(token)
            .payload.subject
    }
}