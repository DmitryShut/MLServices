package com.shut.mlservice.service

import com.shut.mlservice.model.DetectedObject
import org.springframework.web.multipart.MultipartFile

interface DetectionService {

    fun detectObjects(file: MultipartFile, provider: String, name: String): List<DetectedObject>

    fun detectText(file: MultipartFile, provider: String, name: String): List<DetectedObject>

}