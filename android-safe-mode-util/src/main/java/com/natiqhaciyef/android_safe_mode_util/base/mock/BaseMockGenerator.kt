package com.natiqhaciyef.android_safe_mode_util.base.mock

import com.natiqhaciyef.android_safe_mode_util.constants.DATA_NOT_FOUND
import com.natiqhaciyef.android_safe_mode_util.constants.NOT_PRIMARY_CONSTRUCTOR_FOUND
import com.natiqhaciyef.android_safe_mode_util.constants.NOT_VALID_REQUEST
import com.natiqhaciyef.android_safe_mode_util.constants.SOMETHING_WENT_WRONG
import com.natiqhaciyef.android_safe_mode_util.constants.SUCCESS
import com.natiqhaciyef.android_safe_mode_util.constants.USER_NO_PERMISSION
import com.natiqhaciyef.android_safe_mode_util.models.io.CrudModel
import com.natiqhaciyef.android_safe_mode_util.models.io.SafeNetworkResult
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

// create checking architecture that class names should end with MockGenerator extension
abstract class BaseMockGenerator<In, Out : Any> {
    abstract var createdMock: Out
    abstract var takenRequest: In

    fun createInstance(): BaseMockGenerator<In, Out> = this

    abstract fun getMock(
        action: ((In) -> Out?)?
    ): Out


    companion object{
        class MockRequestException(val result: String?): Exception(result ?: NOT_VALID_REQUEST)
    }
}

fun <T : BaseMockGenerator<*, *>, In> generateMockerClass(
    mockClass: KClass<T>,
    request: In
): T {
    val primaryConstructor = mockClass.primaryConstructor
        ?: throw IllegalArgumentException(NOT_PRIMARY_CONSTRUCTOR_FOUND)
    return primaryConstructor.call(request)
}

// OPTIMIZE: this code block should be optimized and integrated to ASML library
fun <T : Any> handleSafeNetworkResultMock(
    obj: T?,
    crudModel: CrudModel,
    resultCode: Int = 200,
    message: String = SUCCESS,
    hasPermission: Boolean = true,
    isBlocked: Boolean = false,
): SafeNetworkResult<T> {
    return when {
        obj == null ->
            SafeNetworkResult(data = null, result = crudModel.copy(
                resultCode = 404,
                message = DATA_NOT_FOUND
            ))

        resultCode !in 200..299 ->
            SafeNetworkResult(
                data = null,
                result = crudModel.copy(resultCode = resultCode, message = SOMETHING_WENT_WRONG)
            )

        !hasPermission ->
            SafeNetworkResult(data = null, result = crudModel.copy(
                resultCode = 401,
                message = USER_NO_PERMISSION
            ))

        isBlocked ->
            SafeNetworkResult(data = null, result = crudModel.copy(
                resultCode = 403,
                message = USER_NO_PERMISSION
            ))

        else ->
            SafeNetworkResult(data = obj, result = crudModel.copy(
                resultCode = resultCode,
                message = message
            ))
    }
}