package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.providers.Function
import com.shut.mlservice.providers.Providers
import org.springframework.web.multipart.MultipartFile

interface DetectionService {

    fun detect(file: MultipartFile, provider: Providers, name: String, function: Function): UserDetectingResult

    fun getProviders(function: Function): List<String>

}