package com.momsitter.user.service

import com.momsitter.common.auth.JwtTokenProvider
import com.momsitter.common.auth.TokenInfo
import com.momsitter.common.exception.InvalidInputException
import com.momsitter.common.status.ROLE
import com.momsitter.user.dto.*
import com.momsitter.user.entity.Children
import com.momsitter.user.entity.Parents
import com.momsitter.user.entity.User
import com.momsitter.user.entity.UserRole
import com.momsitter.user.extension.toDto
import com.momsitter.user.extension.toEntity
import com.momsitter.user.repository.*
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Transactional
@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val sitterRepository: SitterRepository,
    private val parentsRepository: ParentsRepository,
    private val childrenRepository: ChildrenRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    /**
     * Token
     */
    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    /**
     * 회원 가입
     */
    fun signUp(userDtoRequest: UserDtoRequest): Boolean {
        var user: User? = userRepository.findByLoginId(userDtoRequest.loginId)
        if (user != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        // 사용자 정보 저장
        saveMyInfo(userDtoRequest)

        user = userRepository.findByLoginId(userDtoRequest.loginId)

        // 권한 저장
        val userRole = UserRole(ROLE.USER, user!!, null)
        userRoleRepository.save(userRole)

        return true
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