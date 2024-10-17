package com.natiqhaciyef.android_safe_mode_util.models.io


data class SafeResult<out T>(
    val status: Status,
    val data: T? = null,
    var exception: Exception? = Exception(),
    var errorCode: Int = 0,
    var msg: String? = null,
    var isIdle: Boolean = false,
    var isLoading: Boolean = false
) {
    companion object {
        fun <T> success(data: T): SafeResult<T> {
            return SafeResult(status = Status.SUCCESS, data = data)
        }

        fun <T> error(
            exception: Exception, data: T? = null,
            errorCode: Int = 0, msg: String? = null
        ): SafeResult<T> {
            return SafeResult(
                status = Status.ERROR,
                exception = exception,
                data = data,
                msg = msg,
                errorCode = errorCode
            )
        }

        fun <T> loading(data: T? = null): SafeResult<T> {
            return SafeResult(Status.LOADING, data, null)
        }

        fun idle(): SafeResult<Nothing> {
            return SafeResult(status = Status.IDLE, isIdle = true)
        }

        fun void(): SafeResult<Unit> {
            return SafeResult(status = Status.VOID, isIdle = false)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    IDLE,
    VOID,
}