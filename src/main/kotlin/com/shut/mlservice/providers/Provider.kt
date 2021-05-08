package com.shut.mlservice.providers

import com.shut.mlservice.model.DetectedObject
import org.springframework.web.multipart.MultipartFile

interface Provider {

    fun detectObjects(file: MultipartFile): List<DetectedObject>

    fun detectText(file: MultipartFile): List<DetectedObject>
}