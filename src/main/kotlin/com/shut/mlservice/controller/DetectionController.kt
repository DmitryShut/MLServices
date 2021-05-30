package com.shut.mlservice.controller

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
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectObjects(file, provider, principal.name))

    @GetMapping("/objects/providers")
    fun getObjectProviders() =
        ResponseEntity.ok(detectionService.getObjectProviders())

    @GetMapping("/text/providers")
    fun getTextProviders() =
        ResponseEntity.ok(detectionService.getTextProviders())

    @GetMapping("/face/providers")
    fun getFaceProviders() =
        ResponseEntity.ok(detectionService.getFaceProviders())

    @PostMapping("/text")
    fun detectText(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: Providers,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectText(file, provider, principal.name))

    @PostMapping("/face")
    fun detectFace(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: Providers,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectFace(file, provider, principal.name))

}