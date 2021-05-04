package com.shut.mlservice.providers

import com.google.cloud.vision.v1.*
import com.google.protobuf.ByteString
import com.shut.mlservice.BoundingRectangle
import com.shut.mlservice.Coordinate
import com.shut.mlservice.DetectedObject
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.annotation.PreDestroy
import javax.imageio.ImageIO
import kotlin.math.roundToInt


@Service
class GoogleProvider : Provider {

    private val vision: ImageAnnotatorClient = ImageAnnotatorClient.create()

    override fun detectObjects(file: MultipartFile): List<DetectedObject> {
        val image = ImageIO.read(file.inputStream)
        val width = image.width
        val height = image.height
        val img = Image.newBuilder().setContent(ByteString.copyFrom(file.bytes)).build()
        val request = AnnotateImageRequest.newBuilder().addAllFeatures(
            listOf(
                Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build(),
                Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build()
            )
        ).setImage(img).build()
        val response = vision.batchAnnotateImages(listOf(request))
        return response.responsesList.flatMap { annotateImageResponse ->
            annotateImageResponse.localizedObjectAnnotationsList
        }.map { localizedObjectAnnotation ->
            DetectedObject(
                localizedObjectAnnotation.name,
                getBoundingRectangle(localizedObjectAnnotation.boundingPoly.normalizedVerticesList, width, height)
            )
        }
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
        val response = vision.batchAnnotateImages(listOf(request))
        return response.responsesList.flatMap { annotateImageResponse ->
            annotateImageResponse.textAnnotationsList
        }.map { textAnnotation ->
            DetectedObject(
                textAnnotation.description,
                getBoundingRectangleFromVertixes(textAnnotation.boundingPoly.verticesList, width, height)
            )
        }
    }

    fun getBoundingRectangle(normalizedVertexes: List<NormalizedVertex>, width: Int, height: Int): BoundingRectangle {
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

    fun getBoundingRectangleFromVertixes(normalizedVertexes: List<Vertex>, width: Int, height: Int): BoundingRectangle {
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