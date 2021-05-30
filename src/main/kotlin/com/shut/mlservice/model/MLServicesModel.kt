package com.shut.mlservice.model

data class MLServicesModel(
    val text: List<String>,
    val coordinates: List<List<Float>>
)