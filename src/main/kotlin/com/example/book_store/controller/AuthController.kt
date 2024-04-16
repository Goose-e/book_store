package com.example.book_store.controller

import com.example.book_store.dto.UserDto
import com.example.book_store.exceptions.UserNotFoundException
import com.example.book_store.map.Mapper
import com.example.book_store.models.User
import com.example.book_store.repo.UserRepository
import com.example.book_store.service.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier


@RestController
@RequestMapping("/user", produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userService:UserService,
    private val userRepository: UserRepository
        ) {
    val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    @GetMapping("/users/{id}")
    @Throws(UserNotFoundException::class)
    fun getUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<UserDto?>? {
        val user = Optional.ofNullable(userRepository.findById(userId)
            .orElseThrow<RuntimeException>(Supplier<RuntimeException> {
                UserNotFoundException(
                   "User with id=$userId not found"
                )
            })
        )
        val userDTO: UserDto = Mapper.mapUserToUserDTO(user.get())
        return ResponseEntity.ok().body<UserDto>(userDTO)
    }

    @GetMapping("/users")
    fun getAllUsers(): MutableIterable<User> {
        return userRepository.findAll()
    }
    @PostMapping("/createUserDTO")
    fun registerUser(@RequestBody signupRequest: SignUpRequest): ResponseEntity<*>? {
        if (userRepository.existsByLogin(signupRequest.getLogin())) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Username is exist"))
        }

        val pupil: User = UserRepository.findUserByLogin(
            signupRequest.getName(),
            signupRequest.getLastname(),
            signupRequest.getPatronymic()
        )


        if (signupRequest.getRole().equals("pupil")) {
            if (pupil == null) {
                return ResponseEntity
                    .badRequest()
                    .body<Any>(MessageResponse("Error: Pupil is not exist"))
            }
        } else if (signupRequest.getRole().equals("teacher") || signupRequest.getRole().equals("Director")) {
            if (teacher == null) {
                return ResponseEntity
                    .badRequest()
                    .body<Any>(MessageResponse("Error: Teacher is not exist"))
            }
        }

        val user: User = User(
            signupRequest.getLogin(),
            passwordEncoder.encode(signupRequest.getPassword()),
            EStatus.getId(signupRequest.getStatus())
        )

        val reqRoles: Set<String> = signupRequest.getRole()
        val roles: MutableSet<Role> = HashSet<Role>()

        if (reqRoles == null) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Your role is null"))
            /*            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
              roles.add(userRole);*/
        } else {
            reqRoles.forEach(Consumer<String> { r: String? ->
                when (r) {
                    "pupil" -> {
                        val adminRole: Role = roleRepository
                            .findByName(ERole.ROLE_PUPIL)
                            .orElseThrow { java.lang.RuntimeException("Error, Role Pupil is not found") }
                        roles.add(adminRole)
                    }

                    "teacher" -> {
                        val modRole: Role = roleRepository
                            .findByName(ERole.ROLE_TEACHER)
                            .orElseThrow { java.lang.RuntimeException("Error, Role Teacher is not found") }
                        roles.add(modRole)
                    }
                }
            })
        }
        user.setRoles(roles)
        userRepository.save(user)
        val userForId = userRepository.findByLogin(user.getLogin()).orElse(null)

        val role: MutableSet<Role> = HashSet<Role>()
        role.add(Role(ERole.ROLE_PUPIL))

        if (userForId.getRoles().equals(role)) {
            pupil.setUserId(userForId.getId())
            pupil.setEmail(signupRequest.getEmail())
            pupilRepository.save(pupil)
        } else {
            teacher.setUserId(userForId.getId())
            teacher.setEmail(signupRequest.getEmail())
            teacherRepository.save(teacher)
        }
        return ResponseEntity.ok<Any>(MessageResponse("User CREATED"))
    }
}