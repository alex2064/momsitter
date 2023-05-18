package com.momsitter.user.repository

import com.momsitter.user.entity.Children
import com.momsitter.user.entity.Parents
import com.momsitter.user.entity.Sitter
import com.momsitter.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
interface SitterRepository : JpaRepository<Sitter, Long>
interface ParentsRepository : JpaRepository<Parents, Long>
interface ChildrenRepository : JpaRepository<Children, Long>