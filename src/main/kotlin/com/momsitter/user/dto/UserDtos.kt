package com.momsitter.user.dto

import com.momsitter.common.status.Gender
import java.time.LocalDate

data class LoginDto(
    val loginId: String,
    val password: String
)

data class UserDtoRequest(
    val id: Long?,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val loginId: String,
    val password: String,
    val email: String,
    val sitter: SitterDtoRequest?,
    val parents: ParentsDtoRequest?,
)

data class UserDtoResponse(
    val id: Long,
    val name: String,
    val birthDate: String,
    val gender: String,
    val loginId: String,
    val email: String,
    val sitter: SitterDtoResponse?,
    val parents: ParentsDtoResponse?,
)

data class SitterDtoRequest(
    val id: Long?,
    val frCareAge: Int,
    val toCareAge: Int,
    val introduce: String,
)

data class SitterDtoResponse(
    val id: Long,
    val careAge: String,
    val introduce: String,
    val frCareAge: Int,
)

data class ParentsDtoRequest(
    val id: Long?,
    val applyInfo: String,
    val children: List<ChildrenDtoRequest>,
)

data class ParentsDtoResponse(
    val id: Long,
    val applyInfo: String,
    val children: List<ChildrenDtoResponse>,
)

data class ChildrenDtoRequest(
    val id: Long?,
    val birthDate: LocalDate,
    val gender: Gender,
)

data class ChildrenDtoResponse(
    val id: Long,
    val birthDate: String,
    val gender: String,
    val age: Int,
)