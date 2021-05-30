package com.shut.mlservice.service

import com.shut.mlservice.document.User
import com.shut.mlservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service(value = "customUserDetailsService")
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {

    override fun findById(id: String): User = userRepository.findById(id).get()

    override fun findByUsername(username: String): User = userRepository.findByUsername(username)

    override fun save(user: User): User = userRepository.save(user.let { user1 ->
        User(
            password = bCryptPasswordEncoder.encode(user1.password),
            username = user1.username,
            email = user1.email
        )
    })

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username).let { user ->
            org.springframework.security.core.userdetails.User(
                user.username,
                user.password,
                listOf()
            )
        }

}