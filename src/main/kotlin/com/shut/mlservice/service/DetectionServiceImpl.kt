package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.document.UserDetectingResultDto
import com.shut.mlservice.providers.Function
import com.shut.mlservice.providers.Function.*
import com.shut.mlservice.providers.Provider
import com.shut.mlservice.providers.Providers
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DetectionServiceImpl(
    private val faceProviders: Map<Providers, Provider>,
    private val objectProviders: Map<Providers, Provider>,
    private val textProviders: Map<Providers, Provider>,
    private val userDetectingResultService: UserDetectingResultServiceImpl,
    private val userService: UserServiceImpl,
    private val fileService: FileServiceImpl
) : DetectionService {
    override fun detect(file: MultipartFile,
        provider: Providers,
        name: String,
        function: Function
    ): UserDetectingResultDto =
        when (function) {
            OBJECTS -> objectProviders.getValue(provider).detectObjects(file)
            TEXT -> textProviders.getValue(provider).detectText(file)
            FACES -> faceProviders.getValue(provider).detectFace(file)
        }.let { detectedObjectList ->
            val url = fileService.upload(file)
            val userDetectingResult = UserDetectingResult(
                userId = userService.findByUsername(name).id,
                result = detectedObjectList,
                url = url,
                provider = provider.toString(),
                rating = null,
                option = OBJECTS.name
            )
            userDetectingResultService.save(userDetectingResult)
        }.let {
                UserDetectingResultDto(
                    it.id.toHexString(),
                    it.userId.toHexString(),
                    it.result,
                    it.option,
                    it.url,
                    it.provider,
                    it.rating
                )
            }
    override fun getProviders(function: Function): List<String> =
        when (function) {
            OBJECTS -> objectProviders.keys.map { it.name }
            TEXT -> textProviders.keys.map { it.name }
            FACES -> faceProviders.keys.map { it.name }
        }
}