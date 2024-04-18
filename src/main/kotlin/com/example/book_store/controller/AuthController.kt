package com.example.book_store.controller

import com.example.book_store.jwt.JwtProvider
import com.example.book_store.models.LoginUser
import com.example.book_store.models.NewUserDto
import com.example.book_store.models.User
import com.example.book_store.models.enum.RoleEnum.USER
import com.example.book_store.repo.RoleRepository
import com.example.book_store.repo.UserRepository
import com.example.book_store.response.JwtResponse
import com.example.book_store.response.ResponseMessage
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authenticationManager: AuthenticationManager,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val encoder: PasswordEncoder,
    val jwtProvider: JwtProvider
) {


    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginUser): ResponseEntity<*> {

        val userCandidate: Optional<User?>? = userRepository.findByLogin(loginRequest.username!!)

        if (userCandidate?.isPresent!!) {
            val user: User = userCandidate.get()
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            val jwt: String = jwtProvider.generateJwtToken(user.login)
            val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority(user.userRole.getName()))
            return ResponseEntity.ok(JwtResponse(jwt, user.login, authorities))
        } else {
            return ResponseEntity(
                ResponseMessage("User not found!"), BAD_REQUEST
            )
        }
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody newUserDto: NewUserDto): ResponseEntity<*> {

        val userCandidate: Optional<User?>? = userRepository.findByLogin(newUserDto.login)

        if (!userCandidate?.isPresent!!) {
            if (usernameExists(newUserDto.login)) {
                return ResponseEntity(
                    ResponseMessage("Username is already taken!"), BAD_REQUEST
                )
            }


            // Creating user's account
            val user = User(
                login = newUserDto.login,
                userAge = newUserDto.userAge,
                password = encoder.encode(newUserDto.password),
                userRole = USER
            )

            userRepository.save(user)

            return ResponseEntity(ResponseMessage("User registered successfully!"), OK)
        } else {
            return ResponseEntity(
                ResponseMessage("User already exists!"), BAD_REQUEST
            )
        }
    }



    private fun usernameExists(username: String): Boolean {
        return userRepository.findByLogin(username)?.isPresent!!
    }

}