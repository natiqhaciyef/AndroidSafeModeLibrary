package com.natiqhaciyef.android_safe_mode_util.base.cache

import com.natiqhaciyef.android_safe_mode_util.base.network.NetworkResult
import com.natiqhaciyef.android_safe_mode_util.constants.NULL_PROPERTY
import com.natiqhaciyef.android_safe_mode_util.constants.SUCCESS
import com.natiqhaciyef.android_safe_mode_util.constants.ZERO
import com.natiqhaciyef.android_safe_mode_util.models.io.CrudModel
import com.natiqhaciyef.android_safe_mode_util.models.io.SafeNetworkResult
import com.natiqhaciyef.android_safe_mode_util.util.jsonMakerWithGson
import com.natiqhaciyef.android_safe_mode_util.util.toSQLiteString

enum class NetworkStateType {
    CONNECTED,
    NOT_STABLE
}

interface BaseCacheHolder {
    val cacheTag: String
    val data: String?
}

abstract class BaseCache<T : BaseCacheHolder> {

    open suspend fun getCachedElement(): String? {
        return null
    }

    open suspend fun collectCache(cache: String) {

    }

    open suspend fun clearCache(cache: String) {

    }
}


object CacheController {
    var networkState = NetworkStateType.CONNECTED
    var requestCount = 0
    var requestLimit = 3

    private fun requestCounterByNetworkState() {
        if (networkState != NetworkStateType.CONNECTED)
            requestCount += 1
        else
            requestCount = 0

        if (requestCount % requestLimit == ZERO) {
            networkState = NetworkStateType.CONNECTED
        }
    }

    suspend fun <T : Any, S : BaseCacheHolder> handleCacheResponseNetwork(
        cache: BaseCache<S>,
        customOperation: suspend () -> T?,
        operation: (suspend () -> NetworkResult<SafeNetworkResult<T>>)?
    ): NetworkResult<SafeNetworkResult<T>> {
        return runCatching {
            requestCounterByNetworkState()

            when (networkState) {
                NetworkStateType.CONNECTED -> {
                    val network = operation?.invoke()
                    val customOperationResult =
                        customOperation.invoke() ?: throw Exception(NULL_PROPERTY)

                    if (network is NetworkResult.Success) {
                        if (network.data.data?.equals(customOperationResult) == false) {
                            autoSave(cache, network.data.data)

                            networkState = NetworkStateType.NOT_STABLE
                            return NetworkResult
                                .Success(
                                    SafeNetworkResult(
                                        CrudModel(200, SUCCESS),
                                        network.data.data
                                    )
                                )
                        }
                    }

                    NetworkResult
                        .Success(
                            SafeNetworkResult(
                                CrudModel(200, SUCCESS),
                                customOperationResult
                            )
                        )
                }

                NetworkStateType.NOT_STABLE -> {
                    val customOperationResult =
                        customOperation.invoke() ?: throw Exception(NULL_PROPERTY)

                    NetworkResult
                        .Success(
                            SafeNetworkResult(
                                CrudModel(200, SUCCESS),
                                customOperationResult
                            )
                        )
                }
            }
        }.getOrElse { throwable ->
            val result = operation?.invoke()
            if (result is NetworkResult.Success) {
                autoSave(cache, result.data.data)
            }

            handleError(operation, throwable)
        }
    }

    suspend fun <T : Any, S : BaseCacheHolder> handleCacheResponseNetworkList(
        cache: BaseCache<S>,
        customOperation: suspend () -> List<T>?,
        operation: (suspend () -> NetworkResult<SafeNetworkResult<List<T>>>)?
    ): NetworkResult<SafeNetworkResult<List<T>>> {
        return runCatching {
            requestCounterByNetworkState()

            when (networkState) {
                NetworkStateType.CONNECTED -> {
                    val customCache = customOperation.invoke()
                    val network = operation?.invoke()

                    if (network is NetworkResult.Success) {
                        if (network.data.data?.equals(customCache) == false) {
                            autoSave(cache, network.data.data)

                            return NetworkResult
                                .Success(
                                    SafeNetworkResult(
                                        CrudModel(200, SUCCESS),
                                        network.data.data
                                    )
                                )
                        }
                    }

                    NetworkResult
                        .Success(SafeNetworkResult(CrudModel(200, SUCCESS), customCache))

                }

                NetworkStateType.NOT_STABLE -> {
                    val customCache = customOperation.invoke()
                    NetworkResult
                        .Success(SafeNetworkResult(CrudModel(200, SUCCESS), customCache))
                }
            }

        }.getOrElse { throwable ->
            val result = operation?.invoke()
            if (result is NetworkResult.Success) {
                autoSave(cache, result.data.data)
            }

            handleError(operation, throwable)
        }
    }

    private suspend fun <T : Any> handleError(
        operation: (suspend () -> NetworkResult<SafeNetworkResult<T>>)?,
        e: Throwable
    ): NetworkResult<SafeNetworkResult<T>> {
        return operation?.invoke() ?: NetworkResult.Exception<SafeNetworkResult<T>>(e).also {
            println("Error: ${e.localizedMessage}")
        }
    }

    private suspend fun <T: Any, S: BaseCacheHolder> autoSave(cache: BaseCache<S>, collectable: T?){
        val data = jsonMakerWithGson(collectable)
        cache.collectCache(data)
    }

    private suspend fun <T: Any, S: BaseCacheHolder> autoSave(cache: BaseCache<S>, collectable: List<T>?){
        val parseList = collectable?.toSQLiteString()
        val data = jsonMakerWithGson(parseList)
        cache.collectCache(data)
    }
}