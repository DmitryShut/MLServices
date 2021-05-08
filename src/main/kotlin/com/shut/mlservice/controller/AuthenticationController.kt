package com.shut.mlservice.controller

import com.shut.mlservice.document.User
import com.shut.mlservice.security.TokenProvider
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/token")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider
) {
    @RequestMapping(value = ["/generate-token"], method = [RequestMethod.POST])
    fun register(@RequestBody loginUser: User): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUser.username,
                loginUser.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok<Any>(token)
    }
}