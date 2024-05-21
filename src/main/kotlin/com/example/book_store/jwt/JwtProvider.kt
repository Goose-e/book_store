package com.example.book_store.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm.HS512
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys.hmacShaKeyFor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets.UTF_8
import java.security.SignatureException
import java.util.*

@Component
class JwtProvider {
    private val logger: Logger = LoggerFactory.getLogger(JwtProvider::class.java)

    @Value("\${app.jwtSecret:2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D}")
    var jwtSecret: String =
        "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D"
    final val key = hmacShaKeyFor(jwtSecret.toByteArray(UTF_8))

    @Value("\${app.jwtExpiration:86400}")
    var jwtExpiration: Int = 86400

    fun generateJwtToken(username: String): String {

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setClaims(mapOf("USERNAME" to username))
            .setExpiration(Date((Date()).time + jwtExpiration * 1000))
            .signWith(key, HS512)
            .compact()
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(authToken)
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
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token)
            .body["USERNAME"].toString()
//        parser()
//            .verifyWith(key)
//            .build()
//            .parseClaimsJws(token)
//
//        jwtUtil.getAllClaimsFromToken(authToken)
//            .get("role", ArrayList::class.java)
//            .map {
//                Role.valueOf((it as Map<String, String>)["authority"])
//            }
    }
}