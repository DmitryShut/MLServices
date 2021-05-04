package com.shut.mlservice.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "provider")
data class Provider(
    @Id
    val id: ObjectId = ObjectId.get(),
    val name: String
)