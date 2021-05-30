package com.shut.mlservice.document

import com.shut.mlservice.model.DetectedObject
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class UserDetectingResultDto(
    val id: String,
    val userId: String,
    val result: List<DetectedObject>,
    val option: String,
    val url: String,
    val provider: String,
    val rating: Int?
)