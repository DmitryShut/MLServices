package com.shut.mlservice.providers

import com.shut.mlservice.model.BoundingRectangle
import com.shut.mlservice.model.Coordinate
import com.shut.mlservice.model.DetectedObject
import com.shut.mlservice.model.MLServicesModel
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile


class MlServicesProvider(private val restTemplate: RestTemplate) : Provider {
    override fun detectObjects(file: MultipartFile): List<DetectedObject> {
        TODO("Not yet implemented")
    }

    override fun detectText(file: MultipartFile): List<DetectedObject> {
        val headers = HttpHeaders()
        headers.setContentType(MediaType.MULTIPART_FORM_DATA)
        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
        body.add("file", file)
        val requestEntity: HttpEntity<MultiValueMap<String, Any>> = HttpEntity(body, headers)
        val serverUrl = "http://localhost:5000/"
        val model = restTemplate.exchange(
            serverUrl,
            HttpMethod.POST,
            requestEntity,
            object : ParameterizedTypeReference<List<MLServicesModel>>() {}
        ).body!!
        return model.map { mlServicesModel ->
            val coordinatesList = mlServicesModel.coordinates
                .map { coordinates -> Coordinate(coordinates[0].toInt(), coordinates[1].toInt()) }
            DetectedObject(
                mlServicesModel.text.joinToString { " " },
                BoundingRectangle(
                    coordinatesList.maxByOrNull { it.y }!!,
                    coordinatesList.maxByOrNull { it.x }!!
                )
            )
        }
    }

    override fun detectFace(file: MultipartFile): List<DetectedObject> {
        TODO("Not yet implemented")
    }
}