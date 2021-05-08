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
        val header = httpServletRequest.getHeader(SecurityJwtConstants.HEADER_STRING)
        var userName: String? = null
        var authToken: String? = null
        if (header != null && header.startsWith(SecurityJwtConstants.TOKEN_PREFIX)) {
            authToken = header.replace(SecurityJwtConstants.TOKEN_PREFIX, "")
            userName = tokenProvider.getUsernameFromToken(authToken)
        } else {
            logger.warn("Couldn't find bearer string, will ignore the header.")
        }
        if (userName != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = customUserDetailsService.loadUserByUsername(userName)
            if (tokenProvider.validateToken(authToken!!, userDetails)) {
                val authentication = tokenProvider.getAuthentication(
                    authToken,
                    SecurityContextHolder.getContext().authentication,
                    userDetails
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                logger.info("Authenticated user $userName, setting security context.")
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }
}