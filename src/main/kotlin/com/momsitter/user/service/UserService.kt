package com.momsitter.user.service

import com.momsitter.common.exception.InvalidInputException
import com.momsitter.user.dto.ParentsDtoRequest
import com.momsitter.user.dto.SitterDtoRequest
import com.momsitter.user.dto.UserDtoRequest
import com.momsitter.user.dto.UserDtoResponse
import com.momsitter.user.entity.Children
import com.momsitter.user.entity.Parents
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
     * 내 정보 저장
     */
    fun saveMyInfo(userDtoRequest: UserDtoRequest): Boolean {
        val user: User = userDtoRequest.toEntity()
        userRepository.save(user)

        userDtoRequest.sitter?.let {
            saveSitter(user.id!!, it)
        }

        userDtoRequest.parents?.let {
            saveParents(user.id!!, it)
        }

        return true
    }

    fun searchUser(id: Long): User =
        userRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")

    /**
     * 내 정보 보기
     */
    fun searchMyInfo(id: Long): UserDtoResponse {
        val user: User = searchUser(id)
        return user.toDto()
    }

    /**
     * 시터 정보 저장
     */
    fun saveSitter(userId: Long, sitterDtoRequest: SitterDtoRequest): Boolean {
        val user: User = searchUser(userId)
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
    fun saveParents(userId: Long, parentsDtoRequest: ParentsDtoRequest): Boolean {
        val user: User = searchUser(userId)

        // 수정인 경우 같이 들어오지 않은 children 은 삭제
        parentsDtoRequest.id?.let {
            val childrenIds: List<Long> = parentsDtoRequest.children.map { c -> c.id ?: 0 }
            childrenRepository.deleteByParentsIdAndIdNotIn(it, childrenIds)
        }

        val parents: Parents = parentsDtoRequest.toEntity(user)
        parentsRepository.save(parents)

        val children: List<Children> = parentsDtoRequest.children.toEntity(parents)
        childrenRepository.saveAll(children)

        return true
    }

    /**
     * 부모 정보 삭제
     */
    fun removeParents(id: Long): Boolean {
        childrenRepository.findByParentsId(id).let {
            it.forEach { c -> childrenRepository.deleteById(c.id!!) }
        }
        parentsRepository.deleteById(id)

        return true
    }

}