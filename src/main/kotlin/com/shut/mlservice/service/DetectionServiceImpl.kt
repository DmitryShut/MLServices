package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.providers.Function
import com.shut.mlservice.providers.Function.*
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

    override fun detect(file: MultipartFile, provider: Providers, name: String, function: Function): UserDetectingResult =
        objectProviders.getValue(provider).let { provider1 ->
            when(function){
                OBJECTS -> provider1.detectObjects(file)
                TEXT -> provider1.detectText(file)
                FACES -> provider1.detectFace(file)
            }.let { detectedObjectList ->
                    val url = fileService.upload(file)
                    userDetectingResultService.save(
                        UserDetectingResult(
                            userId = userService.findByUsername(name).id,
                            result = detectedObjectList,
                            url = url,
                            provider = provider.toString(),
                            rating = null,
                            option = OBJECTS.name
                        )
                    )
                }
        }

    override fun getProviders(function: Function): List<String> =
        when(function){
            OBJECTS -> objectProviders.keys.map { it.name }
            TEXT -> textProviders.keys.map { it.name }
            FACES -> faceProviders.keys.map { it.name }
        }

}