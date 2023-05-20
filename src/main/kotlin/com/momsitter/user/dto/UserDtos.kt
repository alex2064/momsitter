package com.momsitter.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.momsitter.common.annotation.ValidEnum
import com.momsitter.common.status.Gender
import com.momsitter.user.extension.toLocalDate
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate

data class LoginDto(
    val loginId: String,
    val password: String
)

data class UserDtoRequest(
    val id: Long?,

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("birthDate")
    private val _birthDate: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = Gender::class, message = "MAN 이나 WOMAN 중 하나를 선택해주세요")
    @JsonProperty("gender")
    private val _gender: String?,

    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    @JsonProperty("password")
    private val _password: String?,

    @field:NotBlank
    @field:Email
    @JsonProperty("email")
    private val _email: String?,

    val sitter: SitterDtoRequest?,
    val parents: ParentsDtoRequest?,
) {
    val name: String
        get() = _name!!
    val birthDate: LocalDate
        get() = _birthDate!!.toLocalDate()
    val gender: Gender
        get() = Gender.valueOf(_gender!!)
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val email: String
        get() = _email!!
}

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