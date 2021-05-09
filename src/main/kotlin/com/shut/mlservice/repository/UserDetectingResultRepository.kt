package com.shut.mlservice.repository

import com.shut.mlservice.document.UserDetectingResult
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDetectingResultRepository : MongoRepository<UserDetectingResult, String>