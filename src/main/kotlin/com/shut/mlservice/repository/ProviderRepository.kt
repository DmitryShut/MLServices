package com.shut.mlservice.repository

import com.shut.mlservice.document.Provider
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProviderRepository : MongoRepository<Provider, String>