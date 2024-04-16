//package com.example.book_store.service.impl
//
//import com.example.book_store.models.User
//import com.example.book_store.models.enum.RoleEnum
//import com.fasterxml.jackson.annotation.JsonIgnore
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.userdetails.UserDetails
//import kotlin.math.log
//
//
//class UserDetailsImpl(
//
//private val id:Long?,
//private val login:String,
//    @JsonIgnore
//private val  password:String,
//private val userAge:Int,
//private val authorities: Collection<GrantedAuthority?>? = null
//):UserDetails{
//
//    fun build(user: User): UserDetailsImpl {
//
//        val authorities: List<GrantedAuthority> = RoleEnum.USER
//
//        return UserDetailsImpl(
//            user.userId,
//            user.login,
//            user.password,
//            user.userAge,
//            authorities,
//        )
//    }
//    override fun getAuthorities(): Collection<GrantedAuthority?>? = authorities
//
//    override fun getPassword(): String = password
//
//    override fun getUsername(): String = login
//
//    override fun isAccountNonExpired(): Boolean = true
//
//    override fun isAccountNonLocked(): Boolean = true
//    override fun isCredentialsNonExpired(): Boolean = true
//
//    override fun isEnabled(): Boolean =true
//}