package com.momsitter.playground.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class KotlinJpa(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0L,

    val strValue: String = "A",
    val strValueDefaultA: String = "A",
    val strValueNull: String? = "A",
    val strValueNullDefaultA: String? = "A",

    val intValue: Int = 0,
    val intValueDefaultZero: Int = 0,
    val intValueNull: Int? = 0,
    val intValueNullDefaultZero: Int? = 0,

    val longValue: Long = 0L,
    val longValueDefaultZero: Long = 0L,
    val longValueNull: Long? = 0L,
    val longValueNullDefaultZero: Long? = 0L,

    val doubleValue: Double = 0.0,
    val doubleValueDefaultZero: Double = 0.0,
    val doubleValueNull: Double? = 0.0,
    val doubleValueNullDefaultZero: Double? = 0.0,

    @Temporal(TemporalType.DATE)
    val localDateValue: LocalDate = LocalDate.now(),
    @Temporal(TemporalType.DATE)
    val localDateValueDefaultNow: LocalDate = LocalDate.now(),
    @Temporal(TemporalType.DATE)
    val localDateValueNull: LocalDate? = LocalDate.now(),
    @Temporal(TemporalType.DATE)
    val localDateValueNullDefaultNow: LocalDate? = LocalDate.now(),

    @Temporal(TemporalType.TIMESTAMP)
    val localDateTimeValue: LocalDateTime = LocalDateTime.now(),
    @Temporal(TemporalType.TIMESTAMP)
    val localDateTimeValueDefaultNow: LocalDateTime = LocalDateTime.now(),
    @Temporal(TemporalType.TIMESTAMP)
    val localDateTimeValueNull: LocalDateTime? = LocalDateTime.now(),
    @Temporal(TemporalType.TIMESTAMP)
    val localDateTimeValueNullDefaultNow: LocalDateTime? = LocalDateTime.now(),
)