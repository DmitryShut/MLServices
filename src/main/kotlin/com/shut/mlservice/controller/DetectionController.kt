package com.shut.mlservice.controller

import com.shut.mlservice.service.DetectionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@CrossOrigin("*")
@RequestMapping("/api/detection")
@RestController
class DetectionController(private val detectionService: DetectionService) {

    @GetMapping("/objects")
    fun detectObjects(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: String,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectObjects(file, provider, principal.name))

    @GetMapping("/text")
    fun detectText(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: String,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectText(file, provider, principal.name))

    @GetMapping("/face")
    fun detectFace(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("provider") provider: String,
        principal: Principal
    ) = ResponseEntity.ok(detectionService.detectFace(file, provider, principal.name))

}