package com.natiqhaciyef.android_safe_mode_util.base.mock

import com.natiqhaciyef.android_safe_mode_util.constants.NOT_PRIMARY_CONSTRUCTOR_FOUND
import com.natiqhaciyef.android_safe_mode_util.constants.NOT_VALID_REQUEST
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