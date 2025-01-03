package com.example.book_store.dao

import com.example.book_store.dto.userDto.GetUserDtoDB
import com.example.book_store.dto.userDto.NewStatusDB
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.User
import com.example.book_store.repo.UserRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserDao(
    private val userRepo: UserRepository
) : CrudRepository<User, Long> {
    override fun <S : User?> save(entity: S & Any): S & Any {
        return userRepo.save(entity)
    }
    fun findByLoginForReg(login:String):User? = userRepo.findByLoginForReg(login)
    override fun findAll(): MutableIterable<User> {
        TODO("Not yet implemented")
    }
    override fun <S : User?> saveAll(entities: MutableIterable<S>): MutableIterable<S> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<User> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    fun findAllUsers(): MutableCollection<GetUserDtoDB> = userRepo.getAllUserDto()
    fun findEntByLogin(login:String): CoreEntity? = userRepo.findByEntLogin(login)
    fun findByLogin(login:String):NewStatusDB? = userRepo.findByLogin(login)
    override fun findAllById(ids: MutableIterable<Long>): MutableIterable<User> {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: User) {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<User>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

//fun findAll() = userRepo.findAll()

}