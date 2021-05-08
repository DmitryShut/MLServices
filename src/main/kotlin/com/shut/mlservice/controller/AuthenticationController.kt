package com.shut.mlservice.controller

import com.shut.mlservice.document.User
import com.shut.mlservice.model.LoginUser
import com.shut.mlservice.security.TokenProvider
import com.shut.mlservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider,
    private val userService: UserService
) {

    @PostMapping("/login")
    fun register(@RequestBody loginUser: LoginUser): ResponseEntity<String> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUser.username,
                loginUser.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok(token)
    }

    @PostMapping("/register")
    fun save(@RequestBody user: User): User {
        return userService.save(user)
    }
}