package com.momsitter.playground.controller

import com.momsitter.common.dto.BaseResponse
import com.momsitter.playground.service.KotlinJpaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/test")
@RestController
class KotlinJpaController(
    private val kotlinJpaService: KotlinJpaService
) {
    @GetMapping("/jpa/{id}")
    fun searchEntity(@PathVariable id: Long): BaseResponse<String> {
        val response = kotlinJpaService.jpaTest(id)
        return BaseResponse(data = response)
    }
}