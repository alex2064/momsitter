package com.momsitter.user.dto

import com.momsitter.common.status.Gender
import java.time.LocalDate

data class UserDtoRequest(
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
    val name: String,
    val birthDate: String,
    val gender: String,
    val loginId: String,
    val email: String,
    val sitter: SitterDtoResponse?,
    val parents: ParentsDtoResponse?,
)

data class SitterDtoRequest(
    val frCareAge: Int,
    val toCareAge: Int,
    val introduce: String,
)

data class SitterDtoResponse(
    val careAge: String,
    val introduce: String,
)

data class ParentsDtoRequest(
    val applyInfo: String,
    val children: List<ChildrenDtoRequest>,
)

data class ParentsDtoResponse(
    val applyInfo: String,
    val children: List<ChildrenDtoResponse>,
)

data class ChildrenDtoRequest(
    val birthDate: LocalDate,
    val gender: Gender,
)

data class ChildrenDtoResponse(
    val birthDate: String,
    val gender: String,
)