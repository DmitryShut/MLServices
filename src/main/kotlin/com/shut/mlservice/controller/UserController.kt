package com.shut.mlservice.controller

import com.shut.mlservice.service.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/user")
@RestController
class UserController(private val userService: UserServiceImpl) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) = ResponseEntity.ok(userService.findById(id))

}