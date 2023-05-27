package com.momsitter.playground.repository

import com.momsitter.playground.entity.KotlinJpa
import org.springframework.data.jpa.repository.JpaRepository

interface KotlinJpaRepository : JpaRepository<KotlinJpa, Long>