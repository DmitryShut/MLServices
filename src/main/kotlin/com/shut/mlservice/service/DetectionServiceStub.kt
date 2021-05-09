package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.model.BoundingRectangle
import com.shut.mlservice.model.Coordinate
import com.shut.mlservice.model.DetectedObject
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@Profile("stub")
class DetectionServiceStub(
    private val userDetectingResultService: UserDetectingResultService,
    private val userService: UserService
) : DetectionService {

    override fun detectObjects(file: MultipartFile, provider: String, name: String): List<DetectedObject> =
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
        listOf(
            DetectedObject(
                "СТУДЕНЧЕСКАЯ СЛУЖБА\\nБЕЗОПА СНОСТИ\\nБГУ\\n",
                BoundingRectangle(
                    Coordinate(261, 568),
                    Coordinate(981, 477)
                )
            ),
            DetectedObject(
                "СТУДЕНЧЕСКАЯ",
                BoundingRectangle(
                    Coordinate(274, 519),
                    Coordinate(619, 477)
                )
            ),
            DetectedObject(
                "СЛУЖБА",
                BoundingRectangle(
                    Coordinate(612, 516),
                    Coordinate(795, 481)
                )
            ),
            DetectedObject(
                "БЕЗОПА",
                BoundingRectangle(
                    Coordinate(261, 568),
                    Coordinate(515, 522)
                )
            ),
            DetectedObject(
                "СНОСТИ",
                BoundingRectangle(
                    Coordinate(588, 562),
                    Coordinate(794, 524)
                )
            ),
            DetectedObject(
                "БГУ",
                BoundingRectangle(
                    Coordinate(825, 568),
                    Coordinate(981, 485)
                )
            )
        ).let { detectedObjectList ->
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