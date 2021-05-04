package com.shut.mlservice.service

import com.shut.mlservice.document.Provider
import com.shut.mlservice.repository.ProviderRepository
import org.springframework.stereotype.Service

@Service
class ProviderService(private val providerRepository: ProviderRepository) {

    fun findAll() = providerRepository.findAll()

    fun findById(id: String) = providerRepository.findById(id)

    fun save(provider: Provider) = providerRepository.save(provider)

    fun delete(id: String) = providerRepository.deleteById(id)

}