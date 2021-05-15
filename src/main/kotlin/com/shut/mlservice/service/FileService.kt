package com.shut.mlservice.service

import com.cloudinary.Cloudinary
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService {

    private val cloudinary = Cloudinary()

    fun upload(file: MultipartFile): String =
        cloudinary.uploader().upload(file.bytes, emptyMap<String, Any>()).getValue("url").toString()

}