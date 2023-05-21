package com.momsitter.user.controller

import com.momsitter.common.auth.TokenInfo
import com.momsitter.common.dto.BaseResponse
import com.momsitter.common.dto.CustomUser
import com.momsitter.user.dto.*
import com.momsitter.user.service.UserService
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/user")
@RestController
class UserController(
    private val userService: UserService
) {

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid userDtoRequest: UserDtoRequest): BaseResponse<Unit> {
        userService.signUp(userDtoRequest)
        return BaseResponse()
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): TokenInfo {
        return userService.login(loginDto)
    }

    /**
     * 내 정보 보기
     */
    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<UserDtoResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val response = userService.searchMyInfo(userId)
        return BaseResponse(data = response)
    }

    /**
     * 내 정보 저장
     */
    @PostMapping("/info")
    fun saveMyInfo(@RequestBody @Valid userDtoRequest: UserDtoRequest): BaseResponse<Unit> {
        userService.saveMyInfo(userDtoRequest)
        return BaseResponse()
    }

    /**
     * 시터 정보 저장
     */
    @PostMapping("/sitter")
    fun saveSitter(@RequestBody sitterDtoRequest: SitterDtoRequest): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        userService.saveSitter(userId, sitterDtoRequest)
        return BaseResponse()
    }

    /**
     * 시터 정보 삭제
     */
    @DeleteMapping("/sitter")
    fun removeSitter(): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        userService.removeSitter(userId)
        return BaseResponse()
    }

    /**
     * 부모 정보 저장
     */
    @PostMapping("/parents")
    fun saveParents(@RequestBody parentsDtoRequest: ParentsDtoRequest): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        userService.saveParents(userId, parentsDtoRequest)
        return BaseResponse()
    }

    /**
     * 부모 정보 삭제
     */
    @DeleteMapping("/parents")
    fun removeParents(): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        userService.removeParents(userId)
        return BaseResponse()
    }
}