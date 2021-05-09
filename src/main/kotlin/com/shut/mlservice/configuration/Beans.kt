package com.shut.mlservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class Beans {

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

}