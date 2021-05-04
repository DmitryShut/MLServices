package com.shut.mlservice.controller

import com.shut.mlservice.service.DetectionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/api/detection")
@RestController
class DetectionController(private val detectionService: DetectionService) {

    @GetMapping("/objects")
    fun detectObjects(@RequestParam("file") file: MultipartFile, @RequestParam("provider") provider: String) =
        ResponseEntity.ok(detectionService.detectObjects(file, provider))

    @GetMapping("/text")
    fun detectText(@RequestParam("file") file: MultipartFile, @RequestParam("provider") provider: String) =
        ResponseEntity.ok(detectionService.detectText(file, provider))

}