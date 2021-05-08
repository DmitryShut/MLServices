package com.shut.mlservice.security

object SecurityJwtConstants {
    const val ACCESS_TOKEN_VALIDITY_SECONDS = (5 * 60 * 60).toLong()
    const val SIGNING_KEY = "sign"
    const val TOKEN_PREFIX = "Bearer"
    const val HEADER_STRING = "Authorization"
    const val AUTHORITIES_KEY = "scopes"
}