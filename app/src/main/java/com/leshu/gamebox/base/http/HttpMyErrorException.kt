package com.leshu.gamebox.base.http

import java.io.IOException

/**
 * 自定义后台返回HttpErrorException
 *
 */
class HttpMyErrorException(val code: Int, override val message: String) : IOException(message) {
    companion object {
        const val ERROR_SYSTEM = -99999
    }
}