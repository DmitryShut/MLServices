package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.model.BoundingRectangle
import com.shut.mlservice.model.Coordinate
import com.shut.mlservice.model.DetectedObject
import com.shut.mlservice.providers.Option
import com.shut.mlservice.providers.Provider
import com.shut.mlservice.providers.Providers
import com.shut.mlservice.providers.Providers.*
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@Profile("stub")
class DetectionServiceStub(
    private val faceProviders: Map<Providers, Provider>,
    private val objectProviders: Map<Providers, Provider>,
    private val textProviders: Map<Providers, Provider>,
    private val userDetectingResultService: UserDetectingResultServiceImpl,
    private val userService: UserServiceImpl,
    private val fileService: FileServiceImpl
) : DetectionService {

    override fun detectObjects(file: MultipartFile, provider: Providers, name: String): UserDetectingResult =
        listOf(
            DetectedObject(
                "Dog",
                BoundingRectangle(
                    Coordinate(658, 291),
                    Coordinate(1280, 1079)
                )
            ),
            DetectedObject(
                "Dog",
                BoundingRectangle(
                    Coordinate(28, 259),
                    Coordinate(745, 1077)
                )
            ),
            DetectedObject(
                "Dog",
                BoundingRectangle(
                    Coordinate(1139, 193),
                    Coordinate(1909, 1078)
                )
            )
        ).let { detectedObjectList ->
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
        listOf(
            DetectedObject(
                "THIS IS A TEST\\nIF YOU CAN\\nREAD THIS\\nALL THE WAY\\nDOWN TO HERE\\nPLEASE",
                BoundingRectangle(
                    Coordinate(261, 568),
                    Coordinate(981, 477)
                )
            )
        ).let { detectedObjectList ->
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
        when (provider) {
            AMAZON -> listOf(
                DetectedObject(
                    "face",
                    BoundingRectangle(
                        Coordinate(155, 896),
                        Coordinate(623, 257)
                    )
                )
            ).let { detectedObjectList ->
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
            GOOGLE -> listOf(
                DetectedObject(
                    "face",
                    BoundingRectangle(
                        Coordinate(25, 712),
                        Coordinate(664, 124)
                    )
                )
            ).let { detectedObjectList ->
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
            AZURE -> listOf(
                DetectedObject(
                    "face",
                    BoundingRectangle(
                        Coordinate(25, 712),
                        Coordinate(664, 124)
                    )
                )
            ).let { detectedObjectList ->
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
            else -> UserDetectingResult(
                userId = userService.findByUsername(name).id,
                result = listOf(),
                url = "url",
                provider = provider.toString(),
                rating = null,
                option = Option.FACES.name
            )
        }

    override fun getObjectProviders(): List<String> =
        objectProviders.keys.map { it.name }

    override fun getFaceProviders(): List<String> =
        faceProviders.keys.map { it.name }

    override fun getTextProviders(): List<String> =
        textProviders.keys.map { it.name }

}