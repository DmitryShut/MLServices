package com.shut.mlservice.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authToken = httpServletRequest.getHeader(SecurityJwtConstants.HEADER_STRING)
            .replace(SecurityJwtConstants.TOKEN_PREFIX, "")
        val authentication = tokenProvider.getAuthentication(authToken)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        request.requestURI.contains("/auth")

}