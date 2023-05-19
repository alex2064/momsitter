package com.momsitter.user.extension

import com.momsitter.user.dto.*
import com.momsitter.user.entity.Children
import com.momsitter.user.entity.Parents
import com.momsitter.user.entity.Sitter
import com.momsitter.user.entity.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun UserDtoRequest.toEntity(id: Long): User =
    User(name, birthDate, gender, loginId, password, email, id)

fun User.toDto(): UserDtoResponse =
    UserDtoResponse(name, birthDate.formatDate(), gender.desc, loginId, email, sitter?.toDto(), parents?.toDto())

fun SitterDtoRequest.toEntity(user: User): Sitter =
    Sitter(frCareAge, toCareAge, introduce, user)

fun Sitter.toDto(): SitterDtoResponse =
    SitterDtoResponse("${frCareAge}세 ~ ${toCareAge}세", introduce)

fun ParentsDtoRequest.toEntity(user: User): Parents =
    Parents(applyInfo, user)

fun Parents.toDto(): ParentsDtoResponse =
    ParentsDtoResponse(applyInfo, children!!.toDto())

fun List<ChildrenDtoRequest>.toEntity(parents: Parents): List<Children> =
    this.map { it.toEntity(parents) }

fun List<Children>.toDto(): List<ChildrenDtoResponse> =
    this.map { it.toDto() }

fun ChildrenDtoRequest.toEntity(parents: Parents): Children =
    Children(birthDate, gender, parents)

fun Children.toDto(): ChildrenDtoResponse =
    ChildrenDtoResponse(birthDate.formatDate(), gender.desc)

fun LocalDate.formatDate(): String =
    this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
