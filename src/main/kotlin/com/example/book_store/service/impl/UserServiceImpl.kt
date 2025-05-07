package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
import com.example.book_store.constant.SysConst.OC_BUGS
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.dao.CartDao
import com.example.book_store.dao.CoreEntityDao
import com.example.book_store.dao.UserDao
import com.example.book_store.dto.HttpResponseBody

import com.example.book_store.dto.userDto.*
import com.example.book_store.jwt.JwtProvider
import com.example.book_store.map.Mapper
import com.example.book_store.map.UserMapper
import com.example.book_store.models.Cart
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.User
import com.example.book_store.models.enum.RoleEnum.USER
import com.example.book_store.models.enum.StatusEnum
import com.example.book_store.models.enum.StatusEnum.CART_ACTUAL
import com.example.book_store.models.enum.StatusEnum.USER_ACTUAL
import com.example.book_store.repo.UserRepository
import com.example.book_store.response.JwtResponse
import com.example.book_store.response.ResponseMessage
import com.example.book_store.service.CoreEntityService
import com.example.book_store.service.GenerationService
import com.example.book_store.service.IUserService
import org.dbs.validator.ErrorInfo
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import kotlin.math.log


@Service
class UserServiceImpl(
        val authenticationManager: AuthenticationManager,
    val userRepository: UserRepository,
    val encoder: PasswordEncoder,
    val jwtProvider: JwtProvider,
    val coreEntityDao: CoreEntityDao,
    val userDao: UserDao,
    val cartDao: CartDao,
    val coreEntityService: CoreEntityService
) : IUserService {
    override fun authenticateUser(loginRequest: LoginUserDto): ResponseEntity<*> {

        val user: User? = userDao.findByLoginForReg(loginRequest.login)
        val coreEntity = userDao.findEntByLogin(loginRequest.login)
        if (coreEntity != null && coreEntity.status.getId() == 3) {
            return ResponseEntity(
                ResponseMessage("User was banned!"), HttpStatus.FORBIDDEN
            )
        }
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

    override fun newUserStatus(newStatusDto: NewStatusDtoRequest): HttpResponseBody<NewStatusDto> {
        val response: HttpResponseBody<NewStatusDto> = NewStatusResponse()
        lateinit var changedUser: NewStatusDto
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val loginAmin = authentication.name
        if (loginAmin.equals(newStatusDto.login)) {
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "It`s you!"))
            response.message = "Self harassment"
        } else {
            newStatusDto.login.let { login ->
                userDao.findEntByLogin(login)?.let { ent ->
                    val userStatus = StatusEnum.getEnum(newStatusDto.userStatusId)
                    coreEntityDao.save(UserMapper.mapChangeUserStatus(ent, userStatus))
                    userDao.findByLogin(login)?.let {
                        changedUser = UserMapper.mapUserToChangeUserDTO(it, ent)
                        response.responseEntity = changedUser
                        response.message = "User Status Changed Successfully "
                    }
                }
                    ?: run {
                        response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "User not found"))
                        response.message = "User not found"
                    }
            }
        }

        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }

    override fun getAllUsers(): HttpResponseBody<AllUsersDto> {
        val response: HttpResponseBody<AllUsersDto> = GetUserResponse()
        val getUsersList: MutableCollection<GetUserDtoDB> = userDao.findAllUsers()
        if (getUsersList.isNotEmpty()) {
            val listUserDto = getUsersList.map { UserMapper.mapUserFromUserListDto(it) }
            response.responseEntity = AllUsersDto(listUsersDto = listUserDto)
            response.responseCode = OC_OK
            response.message = "All users was send"
        } else {
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "Books not found"))
            response.responseCode = OC_BUGS
            response.message = "Users not found"
        }
        return response
    }

    override fun registerUser(newUserDto: NewUserRequestDto): HttpResponseBody<NewUserDto> {
        val response: HttpResponseBody<NewUserDto> = CreateUserResponse()
        val userCandidate: User? = userDao.findByLoginForReg(newUserDto.login)

        userCandidate?.let {
            response.message = "User already exist"
            response.errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "User already exist"))
        } ?: run {
            val validationErrors = validateCreateOrUpdateUserRequestDto(newUserDto)
            if (validationErrors.isNotEmpty()) {
                response.errors.addAll(validationErrors)
                response.message = "some field is empty"
            } else {

                val coreEntity = coreEntityService.createCoreEntity(USER_ACTUAL)

                val user = User(
                    userId = coreEntity.coreEntityId,
                    login = newUserDto.login,
                    userAge = newUserDto.userAge,
                    password = encoder.encode(newUserDto.password),
                    userRole = USER

                )
                val coreEntityCart = coreEntityService.createCoreEntity(CART_ACTUAL)
                val cart = Cart(
                    cartId = coreEntityCart.coreEntityId,
                    userId = user.userId,
                    cartCode = GenerationService.generateCode(),
                    cartPrice = BigDecimal.ZERO
                )
                saveInDB(coreEntity, user, coreEntityCart, cart)
                response.responseEntity = Mapper.mapUserToUserDTO(user)
                response.message = "User Created"
            }
        }
        if (response.errors.isNotEmpty()) response.responseCode = OC_BUGS else response.responseCode = OC_OK
        return response
    }

    fun validateCreateOrUpdateUserRequestDto(newUserDto: NewUserRequestDto): List<ErrorInfo> {
        val errors = mutableListOf<ErrorInfo>()
        if (newUserDto.login.isNullOrBlank()) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "login cannot be null or blank"))
        }
        if (newUserDto.userAge <= 0) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "age cannot be <= 0"))
        }
        if (newUserDto.password.isNullOrBlank()) {
            errors.add(ErrorInfo(INVALID_ENTITY_ATTR, "password cannot be null or blank"))
        }
        return errors
    }

    override fun getUserById(userId: Long?): ResponseEntity<UserDto?>? {
        TODO("Not yet implemented")
    }

    override fun updateUser(userId: Long?, userDetails: UserDto?): ResponseEntity<UserDto?>? {
        TODO("Not yet implemented")
    }

    @Transactional
    fun saveInDB(coreEntity: CoreEntity, user: User, coreEntityCart: CoreEntity, cart: Cart) {
        coreEntityDao.save(coreEntity)
        userDao.save(user)
        coreEntityDao.save(coreEntityCart)
        cartDao.save(cart)
    }
}