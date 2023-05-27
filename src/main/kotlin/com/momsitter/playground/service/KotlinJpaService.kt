package com.momsitter.playground.service

import com.momsitter.playground.entity.KotlinJpa
import com.momsitter.playground.repository.KotlinJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class KotlinJpaService(
    private val kotlinJpaRepository: KotlinJpaRepository
) {
    fun jpaTest(id: Long): String {
//        val entity: KotlinJpa? = kotlinJpaRepository.findByIdOrNull(id)
        val entity = KotlinJpa(id = id)
        kotlinJpaRepository.save(entity)
        println(entity)
        return entity.toString()
    }
}