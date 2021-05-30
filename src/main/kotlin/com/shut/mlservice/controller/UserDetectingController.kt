package com.shut.mlservice.controller

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.document.UserDetectingResultDto
import com.shut.mlservice.service.UserDetectingResultService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/result")
@RestController
class UserDetectingController(private val userDetectingResultService: UserDetectingResultService) {

    @GetMapping("/user/{id}")
    fun findByUserId(
        @PathVariable("id") id: String,
        @RequestParam("provider", required = false) provider: String?,
        @RequestParam("option", required = false) option: String?
    ) =
        ResponseEntity.ok(userDetectingResultService.findByUserId(id, option, provider).map {
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

    @PutMapping
    fun update(@RequestBody userDetectingResult: UserDetectingResultDto) =
        ResponseEntity.ok(userDetectingResultService.update(userDetectingResult))
}