package com.momsitter.user.extension

import com.momsitter.user.dto.*
import com.momsitter.user.entity.Children
import com.momsitter.user.entity.Parents
import com.momsitter.user.entity.Sitter
import com.momsitter.user.entity.User
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun UserDtoRequest.toEntity(): User =
    User(name, birthDate, gender, loginId, password, email, id)

fun User.toDto(): UserDtoResponse =
    UserDtoResponse(id!!, name, birthDate.formatDate(), gender.desc, loginId, email, sitter?.toDto(), parents?.toDto())

fun SitterDtoRequest.toEntity(user: User): Sitter =
    Sitter(frCareAge, toCareAge, introduce, user, id)

fun Sitter.toDto(): SitterDtoResponse =
    SitterDtoResponse(id!!, "${frCareAge}세 ~ ${toCareAge}세", introduce, frCareAge)

fun ParentsDtoRequest.toEntity(user: User): Parents =
    Parents(applyInfo, user, id)

fun Parents.toDto(): ParentsDtoResponse =
    ParentsDtoResponse(id!!, applyInfo, children!!.toDto())

fun List<ChildrenDtoRequest>.toEntity(parents: Parents): List<Children> =
    this.map { it.toEntity(parents) }

fun List<Children>.toDto(): List<ChildrenDtoResponse> =
    this.map { it.toDto() }

fun ChildrenDtoRequest.toEntity(parents: Parents): Children =
    Children(birthDate, gender, parents, id)

fun Children.toDto(): ChildrenDtoResponse =
    ChildrenDtoResponse(id!!, birthDate.formatDate(), gender.desc, calculateAge(birthDate.formatDate()))

fun LocalDate.formatDate(): String =
    this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

fun calculateAge(dateOfBirth: String): Int {
    val currentDate = LocalDate.now()
    val parsedDateOfBirth = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyyMMdd"))
    return Period.between(parsedDateOfBirth, currentDate).years
}
