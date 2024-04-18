package com.example.book_store.models

import com.example.book_store.models.enum.RoleEnum
import jakarta.persistence.*


@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: Long? = null,
    @Column(name = "login")
    val login: String,
    @Column(name = "password")
    val password: String,
    @Column(name = "user_age")
    val userAge: Int,
    @Column(name = "user_role_id")
    var userRole: RoleEnum
) {
    constructor() : this(-1, "", "", -1, RoleEnum.USER)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userId != other.userId) return false
        if (login != other.login) return false
        if (password != other.password) return false
        if (userAge != other.userAge) return false
        if (userRole != other.userRole) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId?.hashCode() ?: 0
        result = 31 * result + login.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + userAge
        result = 31 * result + userRole.hashCode()
        return result
    }
}
