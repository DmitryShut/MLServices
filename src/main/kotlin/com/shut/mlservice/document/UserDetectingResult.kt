package com.shut.mlservice.document

import com.shut.mlservice.model.DetectedObject
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("result")
data class UserDetectingResult(
    @Id
    val id: ObjectId = ObjectId.get(),
    val userId: ObjectId,
    val result: List<DetectedObject>,
    val url: String,
    val provider: String
)