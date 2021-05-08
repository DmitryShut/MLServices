package com.shut.mlservice.model

data class BoundingRectangle(
    val topLeftCorner: Coordinate,
    val bottomRightCorner: Coordinate
)