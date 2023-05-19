package com.momsitter.user.controller

import com.momsitter.common.dto.BaseResponse
import com.momsitter.user.dto.UserDtoRequest
import com.momsitter.user.dto.UserDtoResponse
import com.momsitter.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/user")
@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody userDtoRequest: UserDtoRequest): BaseResponse<Unit> {
        userService.signUp(userDtoRequest)
        return BaseResponse()
    }

    @GetMapping("/info")
    fun searchMyInfo(@RequestParam id: Long): BaseResponse<UserDtoResponse> {
        val response = userService.searchMyInfo(id)
        return BaseResponse(data = response)
    }

    @PostMapping("/info")
    fun modifyMyInfo(@RequestBody userDtoRequest: UserDtoRequest): BaseResponse<Unit> {
        userService.saveMyInfo(userDtoRequest)
        return BaseResponse()
    }
}