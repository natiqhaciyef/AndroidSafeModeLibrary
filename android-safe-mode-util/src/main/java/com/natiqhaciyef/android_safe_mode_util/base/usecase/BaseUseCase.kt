package com.natiqhaciyef.android_safe_mode_util.base.usecase


import com.natiqhaciyef.android_safe_mode_util.constants.EMPTY_RESULT
import com.natiqhaciyef.android_safe_mode_util.models.io.SafeResult
import com.natiqhaciyef.android_safe_mode_util.base.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@UseCase
abstract class BaseUseCase<REPO : BaseRepository, In : Any?, Out : Any?>(
    protected val repository: REPO
) : BaseUseCaseInterface<In, Out> {

    override fun invoke(): Flow<SafeResult<Out>>? {
        return null
    }

    override fun run(data: In): Flow<SafeResult<Boolean>> {
        return flow { emit(SafeResult.error(exception = Exception(EMPTY_RESULT))) }
    }

    override fun operate(data: In): Flow<SafeResult<Out>> {
        return flow { emit(SafeResult.error(exception = Exception(EMPTY_RESULT))) }
    }

    // create auto check and auto use-case generator class
    /***
     * Check annotation is an essential function for
     * defining parameter type in base and sub classes
     * constructor parameters.
     *
     * @return Boolean
     * @author Natig Hajiyev && ProScan app
     */
    protected fun <Z> checkAnnotation(
        type: Class<Z>,
    ): Boolean {
        return type.getAnnotation(UseCase::class.java) != null
    }
}
