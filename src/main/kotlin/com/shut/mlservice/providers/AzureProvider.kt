package com.shut.mlservice.providers

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager
import com.microsoft.azure.cognitiveservices.vision.faceapi.FaceAPI
import com.microsoft.azure.cognitiveservices.vision.faceapi.implementation.FaceAPIImpl
import com.shut.mlservice.model.BoundingRectangle
import com.shut.mlservice.model.Coordinate
import com.shut.mlservice.model.DetectedObject
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AzureProvider : Provider {

    private val computerVisionClient: ComputerVisionClient = ComputerVisionManager
        .authenticate("95a93761938b4024a0f58002ee906cb7")
        .withEndpoint("https://mlservices.cognitiveservices.azure.com/")
    private val faceAPI: FaceAPI = FaceAPIImpl(computerVisionClient.restClient())

    override fun detectObjects(file: MultipartFile): List<DetectedObject> {
        val response = computerVisionClient.computerVision().detectObjectsInStream(file.bytes, null)
        return response.objects().map { detectedObject ->
            DetectedObject(
                detectedObject.objectProperty(),
                BoundingRectangle(
                    Coordinate(
                        detectedObject.rectangle().x(),
                        detectedObject.rectangle().y()
                    ),
                    Coordinate(
                        detectedObject.rectangle().x() + detectedObject.rectangle().w(),
                        detectedObject.rectangle().y() + detectedObject.rectangle().h()
                    )
                )
            )
        }
    }

    override fun detectText(file: MultipartFile): List<DetectedObject> {
        val response = computerVisionClient.computerVision().recognizePrintedTextInStream(false, file.bytes, null)
        return response.regions().map { ocrRegion ->
            val split = ocrRegion.boundingBox().split(",")
            DetectedObject(
                ocrRegion.lines().joinToString(separator = "\n") { line ->
                    line.words().joinToString(separator = " ") { word ->
                        word.text()
                    }
                },
                BoundingRectangle(
                    Coordinate(
                        split[0].toInt(),
                        split[1].toInt()
                    ),
                    Coordinate(
                        split[0].toInt() + split[2].toInt(),
                        split[1].toInt() + split[3].toInt()
                    )
                )
            )
        }
    }

    override fun detectFace(file: MultipartFile): List<DetectedObject> {
        val detectWithStream = faceAPI.faces().detectWithStream(file.bytes, null)
        return detectWithStream.map { detectedFace ->
            DetectedObject(
                "face",
                BoundingRectangle(
                    Coordinate(
                        detectedFace.faceRectangle().left(),
                        detectedFace.faceRectangle().top()
                    ),
                    Coordinate(
                        detectedFace.faceRectangle().left() + detectedFace.faceRectangle().width(),
                        detectedFace.faceRectangle().top() + detectedFace.faceRectangle().height()
                    )
                )
            )
        }
    }
}