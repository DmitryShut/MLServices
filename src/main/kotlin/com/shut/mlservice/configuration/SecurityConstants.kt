package com.shut.mlservice.configuration

object SecurityConstants {
    const val SECRET = "SECRET_KEY"
    const val EXPIRATION_TIME: Long = 900000
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val SIGN_UP_URL = "/api/user"
}