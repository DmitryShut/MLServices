package com.shut.mlservice.service

import com.shut.mlservice.document.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun findById(id: String): User

    fun findByUsername(username: String): User

    fun save(user: User): User
}