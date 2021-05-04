package com.shut.mlservice

data class DetectedObject(
    val objectClass: String,
    val coordinates: BoundingRectangle
)
