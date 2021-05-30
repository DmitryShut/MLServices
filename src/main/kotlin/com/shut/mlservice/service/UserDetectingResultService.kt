package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult

interface UserDetectingResultService {

    fun findByUserId(userId: String, option: String?, provider: String?): List<UserDetectingResult>

    fun save(userDetectingResult: UserDetectingResult): UserDetectingResult

}