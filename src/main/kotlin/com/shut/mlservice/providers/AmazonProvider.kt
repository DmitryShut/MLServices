package com.shut.mlservice.providers

import com.shut.mlservice.model.BoundingRectangle
import com.shut.mlservice.model.Coordinate
import com.shut.mlservice.model.DetectedObject
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.SdkBytes
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.rekognition.RekognitionClient
import software.amazon.awssdk.services.rekognition.model.DetectFacesRequest
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest
import software.amazon.awssdk.services.rekognition.model.DetectTextRequest
import software.amazon.awssdk.services.rekognition.model.Image
import javax.annotation.PreDestroy
import javax.imageio.ImageIO
import kotlin.math.roundToInt

class AmazonProvider : Provider {

    private val rekClient = RekognitionClient.builder()
        .region(Region.US_EAST_1)
        .build()

    override fun detectObjects(file: MultipartFile): List<DetectedObject> {
        val image = ImageIO.read(file.inputStream)
        val width = image.width
        val height = image.height
        val amazonImage = Image.builder()
            .bytes(SdkBytes.fromInputStream(file.inputStream))
            .build()
        val detectObjectsRequest = DetectLabelsRequest.builder()
            .image(amazonImage)
            .build()
        return rekClient.detectLabels(detectObjectsRequest).labels()
            .flatMap { label ->
                label.instances()
                    .map { instance ->
                        DetectedObject(
                            label.name(),
                            instance.boundingBox().let { boundingBox ->
                                BoundingRectangle(
                                    Coordinate(
                                        (boundingBox.left() * width).toInt(),
                                        ((1 - boundingBox.top()) * height).toInt()
                                    ),
                                    Coordinate(
                                        ((boundingBox.left() + boundingBox.width()) * width).roundToInt(),
                                        ((1 - boundingBox.top() - boundingBox.height()) * height).roundToInt()
                                    )
                                )
                            }
                        )
                    }
            }
    }

    override fun detectText(file: MultipartFile): List<DetectedObject> {
        val image = ImageIO.read(file.inputStream)
        val width = image.width
        val height = image.height
        val amazonImage = Image.builder()
            .bytes(SdkBytes.fromInputStream(file.inputStream))
            .build()
        val detectTextRequest = DetectTextRequest.builder()
            .image(amazonImage)
            .build()
        return rekClient.detectText(detectTextRequest).textDetections()
            .map { textDetection ->
                DetectedObject(textDetection.detectedText(),
                    textDetection.geometry().boundingBox().let { boundingBox ->
                        BoundingRectangle(
                            Coordinate(
                                (boundingBox.left() * width).toInt(),
                                ((1 - boundingBox.top()) * height).toInt()
                            ),
                            Coordinate(
                                ((boundingBox.left() + boundingBox.width()) * width).roundToInt(),
                                ((1 - boundingBox.top() - boundingBox.height()) * height).roundToInt()
                            )
                        )
                    }
                )
            }
    }

    override fun detectFace(file: MultipartFile): List<DetectedObject> {
        val image = ImageIO.read(file.inputStream)
        val width = image.width
        val height = image.height
        val amazonImage = Image.builder()
            .bytes(SdkBytes.fromInputStream(file.inputStream))
            .build()
        val detectFacesRequest = DetectFacesRequest.builder()
            .image(amazonImage)
            .build()
        return rekClient.detectFaces(detectFacesRequest).faceDetails()
            .map { textDetection ->
                DetectedObject("face",
                    textDetection.boundingBox().let { boundingBox ->
                        BoundingRectangle(
                            Coordinate(
                                (boundingBox.left() * width).toInt(),
                                ((1 - boundingBox.top()) * height).toInt()
                            ),
                            Coordinate(
                                ((boundingBox.left() + boundingBox.width()) * width).roundToInt(),
                                ((1 - boundingBox.top() - boundingBox.height()) * height).roundToInt()
                            )
                        )
                    }
                )
            }
    }

    @PreDestroy
    fun destroy() {
        rekClient.close()
    }
}