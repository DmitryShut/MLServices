package com.shut.mlservice.security

import com.shut.mlservice.service.UserService
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.io.Serializable
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors


@Service
class TokenProvider(private val userService: UserService) : Serializable {
    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token, Claims::getSubject)
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token, Claims::getExpiration)
    }

    private fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(SecurityJwtConstants.SIGNING_KEY)
            .parseClaimsJws(token)
            .getBody()
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(authentication: Authentication): String {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(SecurityJwtConstants.AUTHORITIES_KEY, authorities)
            .claim("id", userService.findByUsername(authentication.name).id.toString())
            .signWith(SignatureAlgorithm.HS256, SecurityJwtConstants.SIGNING_KEY)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + SecurityJwtConstants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val userName = getUsernameFromToken(token)
        return userName == userDetails.username && !isTokenExpired(token)
    }

    fun getAuthentication(
        token: String?,
        existingAuthentication: Authentication?,
        userDetails: UserDetails?
    ): UsernamePasswordAuthenticationToken {
        val jwtParser: JwtParser = Jwts.parser().setSigningKey(SecurityJwtConstants.SIGNING_KEY)
        val claimsJws: Jws<Claims> = jwtParser.parseClaimsJws(token)
        val claims: Claims = claimsJws.getBody()
        val authorities: Collection<GrantedAuthority> =
            claims[SecurityJwtConstants.AUTHORITIES_KEY].toString().split(",")
                .map { role: String? -> SimpleGrantedAuthority(role) }
        return UsernamePasswordAuthenticationToken(userDetails, "", authorities)
    }
}