package com.shut.mlservice.controller

import com.shut.mlservice.model.BoundingRectangle
import com.shut.mlservice.model.Coordinate
import com.shut.mlservice.model.DetectedObject
import com.shut.mlservice.service.DetectionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/api/fake/detection")
@RestController
class FakeDetectionController {

    @GetMapping("/objects")
    fun detectObjects(@RequestParam("file") file: MultipartFile, @RequestParam("provider") provider: String) =
        ResponseEntity.ok(listOf(
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
        ))

    @GetMapping("/text")
    fun detectText(@RequestParam("file") file: MultipartFile, @RequestParam("provider") provider: String) =
        ResponseEntity.ok(listOf(
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
        ))

}