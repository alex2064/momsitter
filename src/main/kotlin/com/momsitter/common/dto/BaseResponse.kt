package com.momsitter.common.dto

import com.momsitter.common.status.ResultCode

data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val messase: String = ResultCode.SUCCESS.msg,
)

