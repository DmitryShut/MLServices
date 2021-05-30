package com.shut.mlservice.service

import com.shut.mlservice.document.UserDetectingResult
import com.shut.mlservice.document.UserDetectingResultDto
import com.shut.mlservice.repository.UserDetectingResultRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class UserDetectingResultServiceImpl(
    private val userDetectingResultRepository: UserDetectingResultRepository
) : UserDetectingResultService {

    override fun findByUserId(userId: String, option: String?, provider: String?): List<UserDetectingResult> =
        if (option != null && provider != null)
            userDetectingResultRepository.findByUserIdAndOptionAndProvider(ObjectId(userId), option, provider)
        else if (option != null)
            userDetectingResultRepository.findByUserIdAndOption(ObjectId(userId), option)
        else if (provider != null)
            userDetectingResultRepository.findByUserIdAndProvider(ObjectId(userId), provider)
        else
            userDetectingResultRepository.findByUserId(ObjectId(userId))

    override fun save(userDetectingResult: UserDetectingResult): UserDetectingResult =
        userDetectingResultRepository.save(userDetectingResult)

    override fun update(userDetectingResult: UserDetectingResultDto): UserDetectingResultDto =
        userDetectingResultRepository.deleteById(userDetectingResult.id).let {
            userDetectingResultRepository.save(UserDetectingResult(
                ObjectId(userDetectingResult.id),
                ObjectId(userDetectingResult.userId),
                userDetectingResult.result,
                userDetectingResult.option,
                userDetectingResult.url,
                userDetectingResult.provider,
                userDetectingResult.rating
            ))
            userDetectingResult
        }

}