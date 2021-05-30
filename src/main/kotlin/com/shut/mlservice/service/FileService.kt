package com.shut.mlservice.service

import org.springframework.web.multipart.MultipartFile

interface FileService {

    fun upload(file: MultipartFile): String

}