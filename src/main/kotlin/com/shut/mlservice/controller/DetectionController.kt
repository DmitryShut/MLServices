package com.shut.mlservice.controller

import com.shut.mlservice.service.DetectionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RequestMapping("/api/detection")
@RestController
class DetectionController(private val detectionService: DetectionService) {

    @PostMapping("/objects")
    fun detectObjects(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: String,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectObjects(file, provider, principal.name))

    @PostMapping("/text")
    fun detectText(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: String,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectText(file, provider, principal.name))

    @PostMapping("/face")
    fun detectFace(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: String,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectFace(file, provider, principal.name))

}