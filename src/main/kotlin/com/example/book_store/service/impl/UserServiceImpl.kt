package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
import com.example.book_store.constant.SysConst.LOCALDATETIME_NULL
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dao.UserDao
import com.example.book_store.dto.*
import com.example.book_store.jwt.JwtProvider
import com.example.book_store.map.Mapper
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.User
import com.example.book_store.models.enum.RoleEnum.USER
import com.example.book_store.models.enum.StatusEnum.USER_ACTUAL
import com.example.book_store.repo.UserRepository
import com.example.book_store.response.JwtResponse
import com.example.book_store.response.ResponseMessage
import com.example.book_store.service.GenerationService.Companion.generateEntityId
import com.example.book_store.service.IUserService
import org.dbs.validator.ErrorInfo
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime.now


@Service
class UserServiceImpl (val authenticationManager: AuthenticationManager,
                       val userRepository: UserRepository,
                       val encoder: PasswordEncoder,
                       val jwtProvider: JwtProvider,
                       val coreEntityDao: CoreEntityDao,
                       val userDao: UserDao): IUserService {
   override fun authenticateUser(loginRequest: LoginUserDto): ResponseEntity<*> {

        val user: User? = userRepository.findByLogin(loginRequest.login)

        user?.let {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.login, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            val jwt: String = jwtProvider.generateJwtToken(user.login)
            val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority(user.userRole.getCode()))
            return ResponseEntity.ok(JwtResponse(jwt, user.login, authorities))
        } ?: run {
            return ResponseEntity(
                ResponseMessage("User not found!"), BAD_REQUEST
            )
        }
    }

    override fun getAllUsers(): MutableIterable<User> {
        TODO("Not yet implemented")
    }

    override fun registerUser(newUserDto: NewUserRequestDto): HttpResponseBody<NewUserDto> {
        val response: HttpResponseBody<NewUserDto> = CreateUserResponse()
        val userCandidate: User? = userRepository.findByLogin(newUserDto.login)

        userCandidate?.let {
            response.message = "User already exist"
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "User already exist"))
        } ?: run {

            val coreEntity = CoreEntity(
                coreEntityId = generateEntityId(),
                createDate = now(),
                deleteDate = LOCALDATETIME_NULL,
                status = USER_ACTUAL
            )

            val user = User(
                userId = coreEntity.coreEntityId,
                login = newUserDto.login,
                userAge = newUserDto.userAge,
                password = encoder.encode(newUserDto.password),
                userRole = USER

            )

            saveInDB(coreEntity, user)
            response.responseEntity = Mapper.mapUserToUserDTO(user)
            response.message = "User Created"
        }
        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }

    override fun getUserById(userId: Long?): ResponseEntity<UserDto?>? {
        TODO("Not yet implemented")
    }

    override fun updateUser(userId: Long?, userDetails: UserDto?): ResponseEntity<UserDto?>? {
        TODO("Not yet implemented")
    }
    @Transactional
    fun saveInDB(coreEntity: CoreEntity, user: User) {
        coreEntityDao.save(coreEntity)
        userDao.save(user)
    }
}