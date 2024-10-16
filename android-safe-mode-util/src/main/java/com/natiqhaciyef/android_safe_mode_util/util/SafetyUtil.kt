package com.natiqhaciyef.android_safe_mode_util.util

import android.util.Log
import com.natiqhaciyef.android_safe_mode_util.base.network.NetworkResult
import com.natiqhaciyef.android_safe_mode_util.constants.EMPTY_FIELD
import com.natiqhaciyef.android_safe_mode_util.constants.NULL_PROPERTY
import com.natiqhaciyef.android_safe_mode_util.constants.NULL_RESULT
import com.natiqhaciyef.android_safe_mode_util.models.io.SafeResult
import com.natiqhaciyef.android_safe_mode_util.models.enums.LoadType
import retrofit2.HttpException
import retrofit2.Response

class SafeCall<IN, OUT>(
    val input: IN? = null,
    val tag: String? = null,
    val expected: OUT? = null,
) {

    data class Builder<IN : Any, OUT : Any>(
        var input: IN? = null,
        var tag: String? = null,
        var expected: OUT? = null,
        private var result: SafeResult<Any>? = null
    ) {
        private val safeCallUtil = SafeCallUtil.getInstance<IN, OUT>()

        fun input(inp: IN) = apply { this.input = inp }

        fun getSafeResult(): SafeResult<Any> {
            return this.result ?: SafeResult.error(Exception(NULL_RESULT))
        }

        fun setLoggingTag(tag: String) = apply {
            this.tag = tag
        }

        fun expected(expectedResult: OUT): SafeResult<Boolean> {
            println(result?.data)
            println(expectedResult)

            return if (expectedResult == result?.data)
                SafeResult.success(true)
            else
                SafeResult.success(false)

        }

        fun parseResult() = try {
            result as SafeResult<OUT>
        } catch (e: Exception) {
            SafeResult.error(e)
        }

        fun run(action: () -> Unit) = apply {
            try {
                Log.d(tag, "Action running...")
                action.invoke()
                Log.d(tag, "Action finished...")

                this.result = SafeResult.void()
                Log.d(tag, "Result set..")

                this.result
            } catch (exc: Exception) {
                Log.e(tag, "${exc.localizedMessage}")
                SafeResult.error(exc)
            }
        }

        fun process(inputAction: (IN) -> Unit) = apply {
            if (this.input != null) {
                try {
                    Log.d(tag, "Action running...")
                    inputAction.invoke(this.input!!)
                    Log.d(tag, "Action finished...")

                    this.result = SafeResult.void()
                    Log.d(tag, "Result set..")

                    this.result
                } catch (exc: Exception) {
                    Log.e(tag, "${exc.localizedMessage}")

                    SafeResult.error(exc)
                }
            } else {
                Log.e(tag, "Input is null")

                SafeResult.error(Exception(EMPTY_FIELD))
            }
        }

        fun commit(outputAction: () -> OUT?) = apply {
            try {
                Log.d(tag, "Action running...")
                val output = outputAction.invoke()
                Log.d(tag, "Action finished...")

                if (output != null) {
                    this.result = SafeResult.success(output)
                    Log.d(tag, "Result set..")

                    this.result
                } else {
                    Log.e(tag, "Output is null...")
                    this.result = SafeResult.error(exception = Exception(NULL_PROPERTY))
                    Log.d(tag, "Result set..")

                    this.result
                }
            } catch (e: Exception) {
                Log.e(tag, "${e.localizedMessage}")
                SafeResult.error(e)
            }
        }

        fun operate(operation: (IN) -> OUT?) = apply {
            try {
                if (input != null) {
                    Log.d(tag, "Action running...")
                    val result = operation.invoke(input!!)
                    Log.d(tag, "Action finished...")

                    if (result != null) {
                        SafeResult.success(result)
                    } else {
                        Log.e(tag, "Output is null...")
                        SafeResult.error(Exception(NULL_RESULT))
                    }
                } else {
                    Log.e(tag, "Input is null...")
                    SafeResult.error(Exception(EMPTY_FIELD))
                }
            } catch (e: Exception) {
                Log.e(tag, "${e.localizedMessage}")
                SafeResult.error(e)
            }
        }

        fun build(): SafeCall<IN, OUT> = SafeCall(
            input = this.input,
            tag = this.tag,
            expected = this.expected,
        )
    }

    companion object {
        suspend fun <T : Any> handleNetworkResponse(
            mock: T? = null,
            handlingType: LoadType = LoadType.DEFAULT,
            execute: suspend () -> Response<T>
        ): NetworkResult<T> {
            val nullProperty = NULL_PROPERTY

            return try {
                when (handlingType) {
                    LoadType.MOCK -> {
                        if (mock != null)
                            NetworkResult.Success(mock)
                        else
                            throw NullPointerException(nullProperty)
                    }

                    LoadType.DEFAULT -> {
                        val response = execute()
                        val body = response.body()
                        if (response.isSuccessful && body != null)
                            NetworkResult.Success(body)
                        else
                            NetworkResult.Error(
                                code = response.code(),
                                message = response.message()
                            )
                    }
                }
            } catch (e: HttpException) {
                NetworkResult.Error(code = e.code(), message = e.message())
            } catch (e: Throwable) {
                NetworkResult.Exception(e)
            }
        }
    }
}

class SafeCallUtil<IN : Any, OUT : Any> {
    val emptyAction: () -> SafeResult<Unit> = { SafeResult.idle() }
    val emptyProcess: (IN) -> SafeResult<Unit> = { SafeResult.idle() }
    val emptyCommit: () -> SafeResult<OUT?> = { SafeResult.idle() }
    val emptyOperation: (IN) -> SafeResult<OUT?> = { SafeResult.idle() }


    companion object {
        @Volatile
        private var INSTANCE: SafeCallUtil<*, *>? = null

        // Generic method to get or create an instance
        fun <IN : Any, OUT : Any> getInstance(): SafeCallUtil<IN, OUT> {
            return INSTANCE as? SafeCallUtil<IN, OUT> ?: synchronized(this) {
                val instance = SafeCallUtil<IN, OUT>()
                INSTANCE = instance
                instance
            }
        }
    }
}