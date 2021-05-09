package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.repository.UserDetectingResultRepository
import org.springframework.stereotype.Service

@Service
class UserDetectingResultService(private val userDetectingResultRepository: UserDetectingResultRepository) {

    fun findAll() = userDetectingResultRepository.findAll()

    fun findById(id: String) = userDetectingResultRepository.findById(id)

    fun save(userDetectingResult: UserDetectingResult) = userDetectingResultRepository.save(userDetectingResult)

    fun delete(id: String) = userDetectingResultRepository.deleteById(id)

}