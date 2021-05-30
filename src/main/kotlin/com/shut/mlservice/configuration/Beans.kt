package com.shut.mlservice.configuration

import com.shut.mlservice.providers.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@Configuration
class Beans {

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun faceProviders(): Map<Providers, Provider> = mapOf(
        Providers.AMAZON to AmazonProvider(),
        Providers.GOOGLE to GoogleProvider(),
        Providers.AZURE to AzureProvider(),
    )

    @Bean
    fun objectProviders(): Map<Providers, Provider> = mapOf(
        Providers.AMAZON to AmazonProvider(),
        Providers.GOOGLE to GoogleProvider(),
        Providers.AZURE to AzureProvider(),
    )

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun textProviders(): Map<Providers, Provider> = mapOf(
        Providers.AMAZON to AmazonProvider(),
        Providers.GOOGLE to GoogleProvider(),
        Providers.AZURE to AzureProvider(),
        Providers.MLSERVICE to MlServicesProvider(restTemplate())
    )

}