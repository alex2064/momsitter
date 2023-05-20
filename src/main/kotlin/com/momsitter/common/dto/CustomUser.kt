package com.momsitter.common.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    val userId: Long,
    username: String,
    password: String,
    authorities: Collection<GrantedAuthority>
) : User(username, password, authorities)