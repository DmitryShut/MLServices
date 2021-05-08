package com.shut.mlservice.providers

import com.shut.mlservice.model.BoundingRectangle
import com.shut.mlservice.model.Coordinate
import com.shut.mlservice.model.DetectedObject
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.SdkBytes
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.rekognition.RekognitionClient
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest
import software.amazon.awssdk.services.rekognition.model.DetectTextRequest
import software.amazon.awssdk.services.rekognition.model.Image
import javax.annotation.PreDestroy
import javax.imageio.ImageIO
import kotlin.math.roundToInt

@Service
class AmazonProvider : Provider {

    private val rekClient = RekognitionClient.builder()
        .region(Region.US_EAST_1)
        .build()

    override fun detectObjects(file: MultipartFile): List<DetectedObject> {
        val image = ImageIO.read(file.inputStream)
        val width = image.width
        val height = image.height
        val souImage: Image = Image.builder()
            .bytes(SdkBytes.fromInputStream(file.inputStream))
            .build()
        val detectLabelsRequest: DetectLabelsRequest = DetectLabelsRequest.builder()
            .image(souImage)
            .build()
        val labelsResponse = rekClient.detectLabels(detectLabelsRequest)
        return labelsResponse.labels().flatMap { label ->
            label.instances()
                .map { instance ->
                    DetectedObject(
                        label.name(),
                        instance.boundingBox().let { boundingBox ->
                            BoundingRectangle(
                                Coordinate(
                                    (boundingBox.left() * width).roundToInt(),
                                    (boundingBox.top() * height).roundToInt()
                                ),
                                Coordinate(
                                    ((boundingBox.left() + boundingBox.width()) * width).roundToInt(),
                                    ((boundingBox.top() + boundingBox.height()) * height).roundToInt()
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
        val souImage: Image = Image.builder()
            .bytes(SdkBytes.fromInputStream(file.inputStream))
            .build()
        val detectTextRequest: DetectTextRequest = DetectTextRequest.builder()
            .image(souImage)
            .build()
        val labelsResponse = rekClient.detectText(detectTextRequest)
        labelsResponse.textDetections()
        return labelsResponse.textDetections().map { textDetection ->
            DetectedObject(textDetection.detectedText(),
                textDetection.geometry().boundingBox().let { boundingBox ->
                    BoundingRectangle(
                        Coordinate(
                            (boundingBox.left() * width).toInt(),
                            (boundingBox.top() * height).toInt()
                        ),
                        Coordinate(
                            ((boundingBox.left() + boundingBox.width()) * width).roundToInt(),
                            ((boundingBox.top() + boundingBox.height()) * height).roundToInt()
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