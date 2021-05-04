package com.shut.mlservice.controller

import com.shut.mlservice.document.Provider
import com.shut.mlservice.service.ProviderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/provider")
@RestController
class ProviderController(private val providerService: ProviderService) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) = ResponseEntity.ok(providerService.findById(id))

    @GetMapping
    fun findAll() = ResponseEntity.ok(providerService.findAll())

    @PostMapping
    fun save(@RequestBody provider: Provider): Provider {
        return providerService.save(provider)
    }

    @DeleteMapping("/{id}")
    fun findAll(@PathVariable id: String) = providerService.delete(id)

}