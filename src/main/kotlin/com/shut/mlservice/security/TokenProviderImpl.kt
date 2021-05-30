package com.shut.mlservice.security

import com.shut.mlservice.service.UserServiceImpl
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*


@Service
class TokenProviderImpl(private val userService: UserServiceImpl) : TokenProvider {

    override fun generateToken(authentication: Authentication): String =
        Jwts.builder()
            .setSubject(authentication.name)
            .claim("id", userService.findByUsername(authentication.name).id.toString())
            .signWith(SignatureAlgorithm.HS256, SecurityJwtConstants.SIGNING_KEY)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + SecurityJwtConstants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
            .compact()


    override fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val jwtParser: JwtParser = Jwts.parser().setSigningKey(SecurityJwtConstants.SIGNING_KEY)
        val body = jwtParser.parseClaimsJws(token).body
        val userDetails = userService.loadUserByUsername(body.subject)
        return UsernamePasswordAuthenticationToken(userDetails, userDetails.password, listOf())
    }
}