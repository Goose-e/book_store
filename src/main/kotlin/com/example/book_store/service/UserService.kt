package com.example.book_store.service

import com.example.book_store.models.User
import com.example.book_store.models.enum.RoleEnum
import com.example.book_store.repo.UserRepository
import com.example.book_store.requestes.SaveUserRequest
import com.example.book_store.service.impl.IUserService
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService (private val repository: UserRepository): IUserService {

    override fun auth() {
        TODO("Not yet implemented") // контроллер -> сервис -> dao -> repo
    }

    override fun registration(request: SaveUserRequest) {
        log.info("Create new user with name=${request.username}")
        repository.save(
            User(
                username = request.username!!,
                login = request.userLogin!!,
                password = request.password!!,
                userAge = request.userAge,
                userRoleId = RoleEnum.USER
            )
        )
    }

    override fun getInfo(id: Long): Optional<User> {
        log.info("Find user with id=$id")
        return repository.findById(id)
    }

}
//front на чём-то(возможно view js(angular js))