package com.foregg.presentation.util

import com.foregg.domain.model.vo.UserVo

object UserInfo {

    var info: UserVo = UserVo()
        private set

    fun updateInfo(info: UserVo) {
        this.info = info
    }

    const val APP_VERSION = "1.0.0"
}