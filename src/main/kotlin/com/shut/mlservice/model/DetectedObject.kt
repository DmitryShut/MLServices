package com.shut.mlservice.model

data class DetectedObject(
    val objectClass: String,
    val coordinates: BoundingRectangle
)
