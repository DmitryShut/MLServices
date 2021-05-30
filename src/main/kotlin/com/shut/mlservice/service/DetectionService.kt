package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.providers.Providers
import org.springframework.web.multipart.MultipartFile

interface DetectionService {

    fun detectObjects(file: MultipartFile, provider: Providers, name: String): UserDetectingResult

    fun detectText(file: MultipartFile, provider: Providers, name: String): UserDetectingResult

    fun detectFace(file: MultipartFile, provider: Providers, name: String): UserDetectingResult

    fun getObjectProviders(): List<String>

    fun getFaceProviders(): List<String>

    fun getTextProviders(): List<String>

}