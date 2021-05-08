package com.shut.mlservice.service

import com.shut.mlservice.document.User
import com.shut.mlservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service(value = "customUserDetailsService")
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    fun findAll() = userRepository.findAll()

    fun findById(id: String) = userRepository.findById(id)

    fun findByUsername(id: String) = userRepository.findByUsername(id)

    fun save(user: User) = userRepository.save(user.let { user1 ->
        User(
            password = bCryptPasswordEncoder.encode(user1.password),
            username = user1.username,
            email = user1.email
        )
    })

    fun delete(id: String) = userRepository.deleteById(id)

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username).let { user ->
            org.springframework.security.core.userdetails.User(
                user.username,
                user.password,
                listOf()
            )
        }

}