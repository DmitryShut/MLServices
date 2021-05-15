package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.repository.UserDetectingResultRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class UserDetectingResultService(private val userDetectingResultRepository: UserDetectingResultRepository) {

    fun findAll() = userDetectingResultRepository.findAll()

    fun findById(id: String) = userDetectingResultRepository.findById(id)

    fun findByUserId(userId: String) = userDetectingResultRepository.findByUserId(ObjectId(userId))

    fun save(userDetectingResult: UserDetectingResult) = userDetectingResultRepository.save(userDetectingResult)

    fun delete(id: String) = userDetectingResultRepository.deleteById(id)

}