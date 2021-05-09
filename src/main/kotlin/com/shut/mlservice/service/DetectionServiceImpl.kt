package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.model.DetectedObject
import com.shut.mlservice.providers.AmazonProvider
import com.shut.mlservice.providers.AzureProvider
import com.shut.mlservice.providers.GoogleProvider
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@Profile("prod")
class DetectionServiceImpl(
    private val googleProvider: GoogleProvider,
    private val amazonProvider: AmazonProvider,
    private val azureProvider: AzureProvider,
    private val userDetectingResultService: UserDetectingResultService,
    private val userService: UserService
) : DetectionService {

    override fun detectObjects(file: MultipartFile, provider: String, name: String): List<DetectedObject> =
        when (provider) {
            "google" -> googleProvider.detectObjects(file)
            "amazon" -> amazonProvider.detectObjects(file)
            "azure" -> azureProvider.detectObjects(file)
            else -> listOf()
        }.let { detectedObjectList ->
            userDetectingResultService.save(
                UserDetectingResult(
                    userId = userService.findByUsername(name).id,
                    result = detectedObjectList,
                    provider = provider
                )
            )
            detectedObjectList
        }

    override fun detectText(file: MultipartFile, provider: String, name: String): List<DetectedObject> =
        when (provider) {
            "google" -> googleProvider.detectText(file)
            "amazon" -> amazonProvider.detectText(file)
            "azure" -> azureProvider.detectText(file)
            else -> listOf()
        }.let { detectedObjectList ->
            userDetectingResultService.save(
                UserDetectingResult(
                    userId = userService.findByUsername(name).id,
                    result = detectedObjectList,
                    provider = provider
                )
            )
            detectedObjectList
        }

    override fun detectFace(file: MultipartFile, provider: String, name: String): List<DetectedObject> =
        when (provider) {
            "google" -> googleProvider.detectFace(file)
            "amazon" -> amazonProvider.detectFace(file)
            "azure" -> azureProvider.detectFace(file)
            else -> listOf()
        }.let { detectedObjectList ->
            userDetectingResultService.save(
                UserDetectingResult(
                    userId = userService.findByUsername(name).id,
                    result = detectedObjectList,
                    provider = provider
                )
            )
            detectedObjectList
        }

}