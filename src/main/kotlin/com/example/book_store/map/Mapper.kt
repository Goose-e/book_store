package com.example.book_store.map

import com.example.book_store.dto.NewUserDto
import com.example.book_store.dto.UserDto
import com.example.book_store.models.User


class Mapper {


    companion object {
        fun mapUserToUserDTO(user: User): NewUserDto = NewUserDto(
                    login = user.login,
                    userRole = user.userRole,
                    password = user.password,
                    userAge = user.userAge)

        fun mapUserDTOToUser(userDTO: UserDto): User =
                User( login =  userDTO.login, password =  userDTO.password, userAge =  userDTO.userAge, userRole =  userDTO.userRole)
    }

}