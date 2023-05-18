package com.momsitter.user.entity

import com.momsitter.common.domain.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_user", columnNames = ["loginId"])]
)
class User(
    @Column(nullable = false, length = 10)
    var name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    var gender: Gender,

    @Column(nullable = false, length = 30)
    var loginId: String,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(nullable = false, length = 30)
    var email: String,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    var sitter: Sitter,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    var parents: Parents,

    id: Long = 0L
) : BaseEntity(id)

@Entity
class Sitter(
    @OneToOne(fetch = FetchType.LAZY)
    var user: User,

    @Column(nullable = false)
    var careFrAge: Int,

    @Column(nullable = false)
    var careToAge: Int,

    @Column(nullable = false, length = 500)
    @Lob
    var introduce: String,

    id: Long = 0L
) : BaseEntity(id)

@Entity
class Parents(
    @OneToOne(fetch = FetchType.LAZY)
    var user: User,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parents")
    var children: MutableList<Children>,

    @Column(nullable = false, length = 500)
    @Lob
    var applyInfo: String,

    id: Long = 0L
) : BaseEntity(id)

@Entity
class Children(
    @ManyToOne(fetch = FetchType.LAZY)
    var parents: Parents,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    var gender: Gender,

    id: Long = 0L
) : BaseEntity(id)

enum class Gender(val desc: String) {
    MAN("남"),
    WOMAN("여")
}