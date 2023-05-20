package com.momsitter.user.controller

import com.momsitter.common.dto.BaseResponse
import com.momsitter.user.dto.ParentsDtoRequest
import com.momsitter.user.dto.SitterDtoRequest
import com.momsitter.user.dto.UserDtoRequest
import com.momsitter.user.dto.UserDtoResponse
import com.momsitter.user.service.UserService
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
    fun signUp(@RequestBody userDtoRequest: UserDtoRequest): BaseResponse<Unit> {
        userService.signUp(userDtoRequest)
        return BaseResponse()
    }

    /**
     * 내 정보 보기
     */
    @GetMapping("/info/{id}")
    fun searchMyInfo(@PathVariable id: Long): BaseResponse<UserDtoResponse> {
        val response = userService.searchMyInfo(id)
        return BaseResponse(data = response)
    }

    /**
     * 내 정보 저장
     */
    @PostMapping("/info")
    fun saveMyInfo(@RequestBody userDtoRequest: UserDtoRequest): BaseResponse<Unit> {
        userService.saveMyInfo(userDtoRequest)
        return BaseResponse()
    }

    /**
     * 시터 정보 저장
     */
    @PostMapping("/sitter/{userId}")
    fun saveSitter(
        @PathVariable userId: Long,
        @RequestBody sitterDtoRequest: SitterDtoRequest,
    ): BaseResponse<Unit> {
        userService.saveSitter(userId, sitterDtoRequest)
        return BaseResponse()
    }

    /**
     * 시터 정보 삭제
     */
    @DeleteMapping("/sitter/{id}")
    fun removeSitter(@PathVariable id: Long): BaseResponse<Unit> {
        userService.removeSitter(id)
        return BaseResponse()
    }

    /**
     * 부모 정보 저장
     */
    @PostMapping("/parents/{userId}")
    fun saveParents(
        @PathVariable userId: Long,
        @RequestBody parentsDtoRequest: ParentsDtoRequest,
    ): BaseResponse<Unit> {
        userService.saveParents(userId, parentsDtoRequest)
        return BaseResponse()
    }

    /**
     * 부모 정보 삭제
     */
    @DeleteMapping("/parents/{id}")
    fun removeParents(@PathVariable id: Long): BaseResponse<Unit> {
        userService.removeParents(id)
        return BaseResponse()
    }
}