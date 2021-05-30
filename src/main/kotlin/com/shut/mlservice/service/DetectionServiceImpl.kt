package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.providers.Option
import com.shut.mlservice.providers.Provider
import com.shut.mlservice.providers.Providers
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@Profile("prod")
class DetectionServiceImpl(
    private val faceProviders: Map<Providers, Provider>,
    private val objectProviders: Map<Providers, Provider>,
    private val textProviders: Map<Providers, Provider>,
    private val userDetectingResultService: UserDetectingResultServiceImpl,
    private val userService: UserServiceImpl,
    private val fileService: FileServiceImpl
) : DetectionService {

    override fun detectObjects(file: MultipartFile, provider: Providers, name: String): UserDetectingResult =
        objectProviders.getValue(provider).detectObjects(file)
            .let { detectedObjectList ->
                val url = fileService.upload(file)
                userDetectingResultService.save(
                    UserDetectingResult(
                        userId = userService.findByUsername(name).id,
                        result = detectedObjectList,
                        url = url,
                        provider = provider.toString(),
                        rating = null,
                        option = Option.OBJECTS.name
                    )
                )
            }

    override fun detectText(file: MultipartFile, provider: Providers, name: String): UserDetectingResult =
        textProviders.getValue(provider).detectText(file)
            .let { detectedObjectList ->
                val url = fileService.upload(file)
                userDetectingResultService.save(
                    UserDetectingResult(
                        userId = userService.findByUsername(name).id,
                        result = detectedObjectList,
                        url = url,
                        provider = provider.toString(),
                        rating = null,
                        option = Option.TEXT.name
                    )
                )
            }

    override fun detectFace(file: MultipartFile, provider: Providers, name: String): UserDetectingResult =
        faceProviders.getValue(provider).detectFace(file)
            .let { detectedObjectList ->
                val url = fileService.upload(file)
                userDetectingResultService.save(
                    UserDetectingResult(
                        userId = userService.findByUsername(name).id,
                        result = detectedObjectList,
                        url = url,
                        provider = provider.toString(),
                        rating = null,
                        option = Option.FACES.name
                    )
                )
            }

    override fun getObjectProviders(): List<String> =
        objectProviders.keys.map { it.name }

    override fun getFaceProviders(): List<String> =
        faceProviders.keys.map { it.name }

    override fun getTextProviders(): List<String> =
        textProviders.keys.map { it.name }

}