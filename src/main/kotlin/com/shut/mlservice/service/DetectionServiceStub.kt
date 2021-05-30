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
                "Cat",
                BoundingRectangle(
                    Coordinate(429, 1001),
                    Coordinate(766, 160)
                )
            ),
            DetectedObject(
                "Cat",
                BoundingRectangle(
                    Coordinate(1392, 1005),
                    Coordinate(1757, 113)
                )
            ),
            DetectedObject(
                "Cat",
                BoundingRectangle(
                    Coordinate(106, 998),
                    Coordinate(506, 164)
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

    override fun detectText(file: MultipartFile, provider: Providers, name: String): UserDetectingResult =
        listOf(
            DetectedObject(
                "THIS IS A TEST\nIF YOU CAN\nREAD THIS\nALL THE WAY\nDOWN TO HERE\nPLEASE,\n",
                BoundingRectangle(
                    Coordinate(458, 737),
                    Coordinate(1503, 268)
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

    override fun detectFace(file: MultipartFile, provider: Providers, name: String): UserDetectingResult =
            listOf(
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

    override fun getObjectProviders(): List<String> =
        objectProviders.keys.map { it.name }

    override fun getFaceProviders(): List<String> =
        faceProviders.keys.map { it.name }

    override fun getTextProviders(): List<String> =
        textProviders.keys.map { it.name }

}