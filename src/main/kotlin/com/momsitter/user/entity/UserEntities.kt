package com.momsitter.user.entity

import com.momsitter.common.domain.BaseEntity
import com.momsitter.common.status.Gender
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_user_login_id", columnNames = ["loginId"])]
)
class User(
    @Column(nullable = false, length = 10)
    val name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 30)
    val email: String,

    id: Long?
) : BaseEntity(id) {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    val sitter: Sitter? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    val parents: Parents? = null
}

@Entity
class Sitter(
    @Column(nullable = false)
    val frCareAge: Int,

    @Column(nullable = false)
    val toCareAge: Int,

    @Column(nullable = false, length = 500)
    @Lob
    val introduce: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_sitter_user_id"))
    val user: User,

    id: Long?
) : BaseEntity(id)

@Entity
class Parents(
    @Column(nullable = false, length = 500)
    @Lob
    val applyInfo: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_parents_user_id"))
    val user: User,

    id: Long?
) : BaseEntity(id) {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parents")
    val children: List<Children>? = null
}

@Entity
class Children(
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_children_parents_id"))
    val parents: Parents,

    id: Long?
) : BaseEntity(id)