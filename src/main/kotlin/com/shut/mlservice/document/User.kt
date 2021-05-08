package com.shut.mlservice.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User(
    @Id
    val id: ObjectId = ObjectId.get(),
    val username: String,
    val email: String,
    val password: String
)