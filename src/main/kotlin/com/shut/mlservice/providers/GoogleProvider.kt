package com.shut.mlservice.providers

import com.google.cloud.vision.v1.*
import com.google.protobuf.ByteString
import com.shut.mlservice.model.BoundingRectangle
import com.shut.mlservice.model.Coordinate
import com.shut.mlservice.model.DetectedObject
import org.springframework.web.multipart.MultipartFile
import javax.annotation.PreDestroy
import javax.imageio.ImageIO
import kotlin.math.roundToInt


class GoogleProvider(private val vision: ImageAnnotatorClient = ImageAnnotatorClient.create()) : Provider {

    override fun detectObjects(file: MultipartFile): List<DetectedObject> {
        val image = ImageIO.read(file.inputStream)
        val img = Image.newBuilder().setContent(ByteString.copyFrom(file.bytes)).build()
        val request = AnnotateImageRequest.newBuilder().addAllFeatures(
            listOf(
                Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build(),
                Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build()
            )
        ).setImage(img).build()
        return processResponse(vision.batchAnnotateImages(listOf(request)), image.width, image.height)
    }

    private fun processResponse(response: BatchAnnotateImagesResponse, width: Int, height: Int): List<DetectedObject> =
        response.responsesList
            .flatMap { annotateImageResponse -> annotateImageResponse.localizedObjectAnnotationsList }
            .map { localizedObjectAnnotation ->
                DetectedObject(
                    localizedObjectAnnotation.name,
                    getBoundingRectangle(
                        localizedObjectAnnotation.boundingPoly.normalizedVerticesList.filter {
                            !it.x.equals(0f) && !it.y.equals(
                                0f
                            )
                        },
                        width,
                        height
                    )
                )
            }

    override fun detectText(file: MultipartFile): List<DetectedObject> {
        val image = ImageIO.read(file.inputStream)
        val width = image.width
        val height = image.height
        val img = Image.newBuilder().setContent(ByteString.copyFrom(file.bytes)).build()
        val request = AnnotateImageRequest.newBuilder().addAllFeatures(
            listOf(
                Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build()
            )
        ).setImage(img).build()
        return vision.batchAnnotateImages(listOf(request)).responsesList
            .flatMap { annotateImageResponse -> annotateImageResponse.textAnnotationsList }
            .map { textAnnotation ->
                DetectedObject(
                    textAnnotation.description,
                    getBoundingRectangleFromVertixes(textAnnotation.boundingPoly.verticesList.filter {
                        it.x != 0 && it.y != 0
                    }, width, height)
                )
            }
    }

    override fun detectFace(file: MultipartFile): List<DetectedObject> {
        val image = ImageIO.read(file.inputStream)
        val width = image.width
        val height = image.height
        val img = Image.newBuilder().setContent(ByteString.copyFrom(file.bytes)).build()
        val request = AnnotateImageRequest.newBuilder().addAllFeatures(
            listOf(
                Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build()
            )
        ).setImage(img).build()
        return vision.batchAnnotateImages(listOf(request)).responsesList
            .flatMap { annotateImageResponse -> annotateImageResponse.faceAnnotationsList }
            .map { faceAnnotation ->
                DetectedObject(
                    "face",
                    getBoundingRectangleFromVertixes(faceAnnotation.fdBoundingPoly.verticesList.filter {
                        it.x != 0 && it.y != 0
                    }, width, height)
                )
            }
    }

    private fun getBoundingRectangle(
        normalizedVertexes: List<NormalizedVertex>,
        width: Int,
        height: Int
    ): BoundingRectangle {
        val maxX = normalizedVertexes.map { it.x * width }.maxOf { it }
        val minX = normalizedVertexes.map { it.x * width }.minOf { it }
        val maxY = normalizedVertexes.map { it.y * height }.maxOf { it }
        val minY = normalizedVertexes.map { it.y * height }.minOf { it }
        return BoundingRectangle(
            Coordinate(
                minX.roundToInt(), maxY.roundToInt()
            ),
            Coordinate(
                maxX.roundToInt(), minY.roundToInt()
            ),
        )
    }

    private fun getBoundingRectangleFromVertixes(
        normalizedVertexes: List<Vertex>,
        width: Int,
        height: Int
    ): BoundingRectangle {
        val maxX = normalizedVertexes.map { it.x }.maxOf { it }
        val minX = normalizedVertexes.map { it.x }.minOf { it }
        val maxY = normalizedVertexes.map { it.y }.maxOf { it }
        val minY = normalizedVertexes.map { it.y }.minOf { it }
        return BoundingRectangle(
            Coordinate(
                minX, maxY
            ),
            Coordinate(
                maxX, minY
            ),
        )
    }


    @PreDestroy
    fun destroy() {
        vision.close()
    }

}