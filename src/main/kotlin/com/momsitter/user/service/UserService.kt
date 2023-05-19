package com.momsitter.user.service

import com.momsitter.common.exception.InvalidInputException
import com.momsitter.user.dto.UserDtoRequest
import com.momsitter.user.dto.UserDtoResponse
import com.momsitter.user.entity.User
import com.momsitter.user.extension.toDto
import com.momsitter.user.extension.toEntity
import com.momsitter.user.repository.ChildrenRepository
import com.momsitter.user.repository.ParentsRepository
import com.momsitter.user.repository.SitterRepository
import com.momsitter.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val sitterRepository: SitterRepository,
    private val parentsRepository: ParentsRepository,
    private val childrenRepository: ChildrenRepository
) {
    fun signUp(userDtoRequest: UserDtoRequest): UserDtoResponse {
        val userList: List<User?> = userRepository.findByLoginId(userDtoRequest.loginId)
        if (userList.isNotEmpty()) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        val user = userDtoRequest.toEntity(userDtoRequest.id)
        userRepository.save(user)

        val sitter = userDtoRequest.sitter?.let {
            val sitterEntity = it.toEntity(user)
            sitterRepository.save(sitterEntity)
            sitterEntity
        }

        val parents = userDtoRequest.parents?.let { p ->
            val parentsEntity = p.toEntity(user)
            parentsRepository.save(parentsEntity)
            val childrenEntityList = p.children.toEntity(parentsEntity)
            childrenRepository.saveAll(childrenEntityList)
            parentsEntity
        }

        return user.toDto()
    }
}