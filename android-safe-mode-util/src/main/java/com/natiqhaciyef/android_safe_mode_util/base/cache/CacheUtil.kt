package com.natiqhaciyef.android_safe_mode_util.base.cache

import com.google.gson.Gson
import com.natiqhaciyef.android_safe_mode_util.base.network.NetworkResult
import com.natiqhaciyef.android_safe_mode_util.constants.NULL_PROPERTY
import com.natiqhaciyef.android_safe_mode_util.constants.SUCCESS
import com.natiqhaciyef.android_safe_mode_util.constants.ZERO
import com.natiqhaciyef.android_safe_mode_util.models.io.CrudModel
import com.natiqhaciyef.android_safe_mode_util.models.io.SafeNetworkResult
import kotlin.reflect.KClass

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


    suspend fun <T : Any, S : BaseCacheHolder> handleCacheResponseNetwork(
        cache: BaseCache<S>,
        customOperation: suspend () -> T?,
        savedCache: (T?) -> String,
        operation: (suspend () -> NetworkResult<SafeNetworkResult<T>>)?
    ): NetworkResult<SafeNetworkResult<T>> {
        return try {
            requestCount += 1

            if (requestCount % requestLimit == ZERO) {
                networkState = NetworkStateType.CONNECTED
            }

            when (networkState) {
                NetworkStateType.CONNECTED -> {
                    val network = operation?.invoke()
                    val customOperationResult =
                        customOperation.invoke() ?: throw Exception(NULL_PROPERTY)

                    if (network is NetworkResult.Success) {
                        if (network.data.data?.equals(customOperationResult) == false) {
                            savedCache.invoke(network.data.data)
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
        } catch (e: Exception) {
            val result = operation?.invoke()
            if (result is NetworkResult.Success) {
                cache.collectCache(savedCache.invoke(result.data.data))
            }

            operation?.invoke() ?: NetworkResult.Exception(Throwable(e.message, e.cause))
        }
    }

    suspend fun <T : Any, S : BaseCacheHolder> handleCacheResponseNetworkList(
        cache: BaseCache<S>,
        customOperation: suspend () -> List<T>?,
        savedCache: (List<T>?) -> String,
        operation: (suspend () -> NetworkResult<SafeNetworkResult<List<T>>>)?
    ): NetworkResult<SafeNetworkResult<List<T>>> {
        return try {

            if (networkState != NetworkStateType.CONNECTED)
                requestCount += 1
            else
                requestCount = 0

            if (requestCount % requestLimit == ZERO) {
                networkState = NetworkStateType.CONNECTED
            }

            when (networkState) {
                NetworkStateType.CONNECTED -> {
                    val customCache = customOperation.invoke()
                    val network = operation?.invoke()

                    if (network is NetworkResult.Success) {
                        if (network.data.data?.equals(customCache) == false) {
                            savedCache.invoke(network.data.data)

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

        } catch (e: Exception) {
            val result = operation?.invoke()
            if (result is NetworkResult.Success) {
                cache.collectCache(savedCache.invoke(result.data.data))
            }

            result ?: NetworkResult.Exception(Throwable(e.message, e.cause))
        }
    }

}