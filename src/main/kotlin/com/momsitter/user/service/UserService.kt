package com.momsitter.user.service

import com.momsitter.common.exception.InvalidInputException
import com.momsitter.user.dto.UserDtoRequest
import com.momsitter.user.dto.UserDtoResponse
import com.momsitter.user.entity.User
import com.momsitter.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.MethodArgumentNotValidException

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun signUp(userDtoRequest: UserDtoRequest): UserDtoResponse {
        val userList: List<User?> = userRepository.findByLoginId(userDtoRequest.loginId)
        if (userList.isEmpty()) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }
    }
}