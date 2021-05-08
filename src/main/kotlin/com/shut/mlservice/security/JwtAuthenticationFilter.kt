package com.shut.mlservice.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider,
    private val customUserDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
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
}