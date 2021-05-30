package com.shut.mlservice.controller

import com.shut.mlservice.document.User
import com.shut.mlservice.model.LoginUser
import com.shut.mlservice.model.Token
import com.shut.mlservice.security.TokenProviderImpl
import com.shut.mlservice.service.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProviderImpl,
    private val userService: UserServiceImpl
) {

    @PostMapping("/login")
    fun login(@RequestBody loginUser: LoginUser): ResponseEntity<Token> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUser.username,
                loginUser.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok(Token(token))
    }

    @PostMapping("/register")
    fun save(@RequestBody user: User): User = userService.save(user)
}