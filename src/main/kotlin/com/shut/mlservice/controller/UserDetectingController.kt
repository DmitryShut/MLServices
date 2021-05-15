package com.shut.mlservice.controller

import com.shut.mlservice.service.UserDetectingResultService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/result")
@RestController
class UserDetectingController(private val userDetectingResultService: UserDetectingResultService) {

    @GetMapping("/user/{id}")
    fun findByUserId(@PathVariable id: String) = ResponseEntity.ok(userDetectingResultService.findByUserId(id))

}