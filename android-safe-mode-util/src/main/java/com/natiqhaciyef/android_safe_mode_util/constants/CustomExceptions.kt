package com.natiqhaciyef.android_safe_mode_util.constants

interface BaseException{
    var msg: String
    var errorCode: Int

    fun BaseException.createLog(tag: String): String{
        return "$tag Code: $errorCode - Message: $msg"
    }
}

sealed interface ResultExceptions: BaseException {

    companion object {
        const val RESULT_EXCEPTION_TAG = "RESULT EXCEPTION TAG => "
    }


    data class UserLoadingFailed(
        override var msg: String = USER_NOT_FOUND,
        override var errorCode: Int = 404
    ) : Exception(), ResultExceptions

    data class UseCaseAnnotationNotFound(
        override var msg: String = CHECKED_USE_CASE_NOT_FOUND_EXCEPTION,
        override var errorCode: Int = 4042
    ) : Exception(), ResultExceptions

    data class TokenCreationFailed(
        override var msg: String = TOKEN_CREATION_FAILED_EXCEPTION,
        override var errorCode: Int = 400
    ) : Exception(), ResultExceptions

    data class RequestFailed(
        override var msg: String = TOKEN_CREATION_FAILED_EXCEPTION,
        override var errorCode: Int = 401
    ) : Exception(), ResultExceptions

    data class FieldsNotFound(
        override var msg: String = ELEMENT_NOT_FOUND,
        override var errorCode: Int = 404
    ) : Exception(), ResultExceptions

    data class UnknownError(
        override var msg: String = UNKNOWN_ERROR,
        override var errorCode: Int = 400
    ) : Exception(), ResultExceptions

    data class CustomIOException(
        override var msg: String = UNKNOWN_ERROR,
        override var errorCode: Int = 400
    ) : Exception(), ResultExceptions

    data class BiometricAuthenticationError(
        override var msg: String = BIOMETRIC_AUTHENTICATION_FAILED,
        override var errorCode: Int = 401
    ) : Exception(), ResultExceptions

}


sealed interface ReflectionExceptions: BaseException{

    companion object {
        const val REFLECTION_EXCEPTION_TAG = "REFLECTION EXCEPTION TAG => "
    }

    data class FieldTypeError(
        override var msg: String = WRONG_FIELD_TYPE_INSERTED,
        override var errorCode: Int = 400
    ) : Exception(), ReflectionExceptions

    data class FieldNameError(
        override var msg: String = FIELD_NAME_NOT_FOUND,
        override var errorCode: Int = 400
    ) : Exception(), ReflectionExceptions
}


sealed interface GeneralExceptions: BaseException{

    data class SomethingWentWrong(
        override var msg: String = SOMETHING_WENT_WRONG,
        override var errorCode: Int = 500
    ) : Exception(), GeneralExceptions

}