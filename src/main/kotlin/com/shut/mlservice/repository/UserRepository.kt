package com.shut.mlservice.repository

import com.shut.mlservice.document.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun findByUsername(username: String): User

}