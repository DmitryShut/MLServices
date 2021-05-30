package com.shut.mlservice.controller

import com.shut.mlservice.document.UserDetectingResultDto
import com.shut.mlservice.providers.Function
import com.shut.mlservice.providers.Providers
import com.shut.mlservice.service.DetectionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RequestMapping("/api/detection")
@RestController
class DetectionController(private val detectionService: DetectionService) {

    @PostMapping("/objects")
    fun detectObjects(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: Providers,
        @RequestParam("function") function: Function,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detect(file, provider, principal.name, function).let {
        UserDetectingResultDto(
            it.id.toHexString(),
            it.userId.toHexString(),
            it.result,
            it.option,
            it.url,
            it.provider,
            it.rating
        )
    })

    @GetMapping("/providers")
    fun getObjectProviders(
        @RequestParam("function") function: Function
    ) = ResponseEntity.ok(detectionService.getProviders(function))
}