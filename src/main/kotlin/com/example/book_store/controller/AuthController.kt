package com.example.book_store.controller

import com.example.book_store.jwt.JwtProvider
import com.example.book_store.models.LoginUser
import com.example.book_store.models.NewUser
import com.example.book_store.models.User
import com.example.book_store.models.enum.RoleEnum
import com.example.book_store.repo.RoleRepository
import com.example.book_store.repo.UserRepository
import com.example.book_store.response.JwtResponse
import com.example.book_store.response.ResponseMessage
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
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
    var authenticationManager: AuthenticationManager,

    var userRepository: UserRepository,

    var roleRepository: RoleRepository,

    var encoder: PasswordEncoder,

    var jwtProvider: JwtProvider
) {


    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginUser): ResponseEntity<*> {

        val userCandidate: Optional<User?>? = userRepository.findByLogin(loginRequest.username!!)

        if (userCandidate?.isPresent!!) {
            val user: User = userCandidate.get()
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
            SecurityContextHolder.getContext().setAuthentication(authentication)
            val jwt: String = jwtProvider.generateJwtToken(user.login!!)
            val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority(user.userRole.getName()))
            return ResponseEntity.ok(JwtResponse(jwt, user.login, authorities))
        } else {
            return ResponseEntity(
                ResponseMessage("User not found!"), HttpStatus.BAD_REQUEST
            )
        }
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody newUser: NewUser): ResponseEntity<*> {

        val userCandidate: Optional<User?>? = userRepository.findByLogin(newUser.login!!)

        if (!userCandidate?.isPresent!!) {
            if (usernameExists(newUser.login!!)) {
                return ResponseEntity(
                    ResponseMessage("Username is already taken!"), HttpStatus.BAD_REQUEST
                )
            }


            // Creating user's account
            val user = User(
                userId = 0,
                login = newUser.login!!,
                userAge = newUser.userAge!!,
                password = encoder.encode(newUser.password),
                userRole = RoleEnum.USER

            )
            user!!.userRole =  roleRepository.findByName("USER_ROLE")

            userRepository.save(user)

            return ResponseEntity(ResponseMessage("User registered successfully!"), HttpStatus.OK)
        } else {
            return ResponseEntity(
                ResponseMessage("User already exists!"), HttpStatus.BAD_REQUEST
            )
        }
    }



    private fun usernameExists(username: String): Boolean {
        return userRepository.findByLogin(username)?.isPresent!!
    }

}