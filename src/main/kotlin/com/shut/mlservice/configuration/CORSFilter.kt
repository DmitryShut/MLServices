package com.shut.mlservice.configuration

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CORSFilter : Filter {
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val res = response as HttpServletResponse
        val req = request as HttpServletRequest
        res.setHeader("Access-Control-Allow-Origin", "*")
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
        res.setHeader(
            "Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, " +
                    "Accept, Accept-Encoding, Accept-Language, Host, Referer, Connection, User-Agent, authorization, " +
                    "sw-useragent, sw-version"
        )

        // Just REPLY OK if request method is OPTIONS for CORS (pre-flight)
        if (req.method == "OPTIONS") {
            res.status = HttpServletResponse.SC_OK
            return
        }
        filterChain.doFilter(request, response)
    }

    override fun destroy() {}
}