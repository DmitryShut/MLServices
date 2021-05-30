package com.shut.mlservice.repository

import com.shut.mlservice.document.UserDetectingResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDetectingResultRepository : MongoRepository<UserDetectingResult, String> {
    fun findByUserId(userId: ObjectId): List<UserDetectingResult>

    fun findByUserIdAndProvider(userId: ObjectId, provider: String): List<UserDetectingResult>

    fun findByUserIdAndOption(userId: ObjectId, option: String): List<UserDetectingResult>

    fun findByUserIdAndOptionAndProvider(userId: ObjectId, option: String, provider: String): List<UserDetectingResult>
}