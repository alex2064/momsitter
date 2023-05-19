package com.momsitter.user.service

import com.momsitter.common.dto.BaseResponse
import com.momsitter.common.exception.InvalidInputException
import com.momsitter.user.dto.ParentsDtoRequest
import com.momsitter.user.dto.SitterDtoRequest
import com.momsitter.user.dto.UserDtoRequest
import com.momsitter.user.dto.UserDtoResponse
import com.momsitter.user.entity.User
import com.momsitter.user.extension.toDto
import com.momsitter.user.extension.toEntity
import com.momsitter.user.repository.ChildrenRepository
import com.momsitter.user.repository.ParentsRepository
import com.momsitter.user.repository.SitterRepository
import com.momsitter.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class UserService(
    private val userRepository: UserRepository,
    private val sitterRepository: SitterRepository,
    private val parentsRepository: ParentsRepository,
    private val childrenRepository: ChildrenRepository
) {
    /**
     * 회원 가입
     */
    fun signUp(userDtoRequest: UserDtoRequest): Boolean {
        val userList: List<User?> = userRepository.findByLoginId(userDtoRequest.loginId)
        if (userList.isNotEmpty()) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        return saveMyInfo(userDtoRequest)
    }

    /**
     * 내 정보 보기
     */
    fun searchMyInfo(id: Long): UserDtoResponse {
        val user = userRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")
        return user.toDto()
    }

    /**
     * 내 정보 업데이트
     */
    fun saveMyInfo(userDtoRequest: UserDtoRequest): Boolean {
        val user = userDtoRequest.toEntity()
        userRepository.save(user)

        val sitter = userDtoRequest.sitter?.let {
            val sitterEntity = it.toEntity(user)
            sitterRepository.save(sitterEntity)
            sitterEntity
        }

        val parents = userDtoRequest.parents?.let { p ->
            val parentsEntity = p.toEntity(user)
            parentsRepository.save(parentsEntity)

            val childrenEntityList = p.children.toEntity(parentsEntity)
            childrenRepository.saveAll(childrenEntityList)
            parentsEntity
        }

        return true
    }

    /**
     * 시터 정보 저장
     */
    fun saveSitter(sitterDtoRequest: SitterDtoRequest): Boolean {
        val user = sitterRepository.findByIdOrNull(sitterDtoRequest.id)?.user ?: throw InvalidInputException(
            "sitter",
            "시터 정보가 확인되지 않습니다."
        )

        val sitter = sitterDtoRequest.toEntity(user)
        sitterRepository.save(sitter)
        return true
    }

    /**
     * 시터 정보 삭제
     */
    fun removeSitter(id: Long): Boolean {
        sitterRepository.deleteById(id)
        return true
    }

    /**
     * 부모 정보 저장
     */
    fun saveParents(parentsDtoRequest: ParentsDtoRequest): Boolean {
        val user = parentsRepository.findByIdOrNull(parentsDtoRequest.id)?.user ?: throw InvalidInputException(
            "sitter",
            "부모 정보가 확인되지 않습니다."
        )

        val parents = parentsDtoRequest.toEntity(user)
        parentsRepository.save(parents)

        val children = parentsDtoRequest.children.toEntity(parents)
        childrenRepository.saveAll(children)

        return true
    }

    /**
     * 부모 정보 삭제
     */
    fun removeParents(id: Long): Boolean {
        childrenRepository.findByParentsId(id)?.let { e -> e.map { childrenRepository.deleteById(it.id!!) } }
        parentsRepository.deleteById(id)
        return true
    }

}