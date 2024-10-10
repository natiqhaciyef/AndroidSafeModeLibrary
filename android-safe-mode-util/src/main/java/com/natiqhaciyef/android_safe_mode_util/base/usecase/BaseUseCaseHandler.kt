package com.natiqhaciyef.android_safe_mode_util.base.usecase

import com.natiqhaciyef.android_safe_mode_util.base.network.NetworkResult
import com.natiqhaciyef.android_safe_mode_util.constants.ONE
import com.natiqhaciyef.android_safe_mode_util.constants.SOMETHING_WENT_WRONG
import com.natiqhaciyef.android_safe_mode_util.constants.UNKNOWN_ERROR
import com.natiqhaciyef.android_safe_mode_util.models.io.SafeResult
import kotlinx.coroutines.flow.flow


fun <OUT : Any, MT: Any> handleFlowResult(
    networkResult: suspend () -> NetworkResult<OUT>,
    operation: (OUT) -> SafeResult<MT>
) = flow {
    emit(SafeResult.loading())

    when(val result = networkResult.invoke()){
        is NetworkResult.Success -> {
            emit(operation.invoke(result.data))
        }

        is NetworkResult.Error -> {
            emit(
                SafeResult.error(
                    msg = result.message ?: UNKNOWN_ERROR,
                    data = null,
                    exception = Exception(result.message),
                    errorCode = result.code
                )
            )
        }

        is NetworkResult.Exception -> {
            emit(
                SafeResult.error(
                msg = result.e.message ?: SOMETHING_WENT_WRONG,
                data = null,
                exception = Exception(result.e),
                errorCode = -ONE
            ))
        }
    }
}

