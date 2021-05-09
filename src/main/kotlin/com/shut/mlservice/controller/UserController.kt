package com.shut.mlservice.controller

import com.shut.mlservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RequestMapping("/api/user")
@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) = ResponseEntity.ok(userService.findById(id))

    @GetMapping
    fun findAll() = ResponseEntity.ok(userService.findAll())

    @DeleteMapping("/{id}")
    fun findAll(@PathVariable id: String) = userService.delete(id)

}