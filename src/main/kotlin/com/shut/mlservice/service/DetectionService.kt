package com.shut.mlservice.service

import com.shut.mlservice.model.DetectedObject
import com.shut.mlservice.providers.AmazonProvider
import com.shut.mlservice.providers.GoogleProvider
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DetectionService(private val googleProvider: GoogleProvider, private val amazonProvider: AmazonProvider) {

    fun detectObjects(file: MultipartFile, provider: String): List<DetectedObject> =
        when(provider){
            "google" -> googleProvider.detectObjects(file)
            "amazon" -> amazonProvider.detectObjects(file)
            else -> listOf()
        }

    fun detectText(file: MultipartFile, provider: String): List<DetectedObject> =
        when(provider){
            "google" -> googleProvider.detectText(file)
            "amazon" -> amazonProvider.detectText(file)
            else -> listOf()
        }

}