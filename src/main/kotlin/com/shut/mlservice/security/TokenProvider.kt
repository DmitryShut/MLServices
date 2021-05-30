package com.shut.mlservice.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

interface TokenProvider {

    fun generateToken(authentication: Authentication): String

    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken
}