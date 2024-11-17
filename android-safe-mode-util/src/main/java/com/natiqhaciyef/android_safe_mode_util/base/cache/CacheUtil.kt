package com.natiqhaciyef.android_safe_mode_util.base.cache

import android.util.Log
import com.natiqhaciyef.android_safe_mode_util.base.network.NetworkResult
import com.natiqhaciyef.android_safe_mode_util.constants.NULL_PROPERTY
import com.natiqhaciyef.android_safe_mode_util.constants.SUCCESS
import com.natiqhaciyef.android_safe_mode_util.models.io.CrudModel
import com.natiqhaciyef.android_safe_mode_util.models.io.SafeNetworkResult

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

    open suspend fun getCachedElementsList(): List<T>? {
        return null
    }

    open suspend fun collectCache(cache: String) {

    }

    open suspend fun collectCacheList(cacheList: List<String>) {

    }


    open suspend fun clearCache(cache: String) {

    }
}

class CacheController<T : Any, S : BaseCacheHolder> {
    companion object {
        private const val CACHE_TAG = "CACHE CONTROLLER TAG => "
    }

    var networkState = NetworkStateType.CONNECTED
    var requestCount = 0
    var requestLimit = 3

    suspend fun handleCacheResponseNetwork(
        cache: BaseCache<S>,
        customOperation: suspend () -> T?,
        savedCache: (T?) -> String,
        operation: (suspend () -> NetworkResult<SafeNetworkResult<T>>)?
    ): NetworkResult<SafeNetworkResult<T>> {
        return try {
            requestCount += 1

            if (requestCount % requestLimit == 0) {
                networkState = NetworkStateType.CONNECTED
            }

            when (networkState) {
                NetworkStateType.CONNECTED -> {
                    var customCache = customOperation.invoke()
                    val network = operation?.invoke()

                    Log.d(CACHE_TAG, "Custom: $customCache")
                    if (customCache == null) {
                        throw IllegalStateException()
                    }

                    if (network is NetworkResult.Success
                        && network.data.data?.equals(customCache) == false
                    ) {
                        cache.collectCache(savedCache.invoke(network.data.data))
                        customCache = network.data.data
                    }

                    NetworkResult
                        .Success(SafeNetworkResult(CrudModel(200, SUCCESS), customCache))
                }

                NetworkStateType.NOT_STABLE -> {
                    val customOperationResult =
                        customOperation.invoke() ?: throw NullPointerException(NULL_PROPERTY)

                    NetworkResult
                        .Success(
                            SafeNetworkResult(
                                CrudModel(200, SUCCESS),
                                customOperationResult
                            )
                        )
                }
            }
        } catch (illegalE: IllegalStateException) {
            val result = operation?.invoke()
            if (result is NetworkResult.Success && result.data.data != null) {
                val saved = savedCache.invoke(result.data.data)
                cache.collectCache(saved)
            }

            Log.d(CACHE_TAG, "Result: $result")
            result ?: NetworkResult.Exception(Throwable(illegalE.message, illegalE.cause))
        } catch (e: Exception) {
            val result = operation?.invoke()
            if (result is NetworkResult.Success) {
                cache.collectCache(savedCache.invoke(result.data.data))
            }

            Log.d(CACHE_TAG, "Exception: ${e.localizedMessage}")
            Log.d(CACHE_TAG, "Result: $result")
            result ?: NetworkResult.Exception(Throwable(e.message, e.cause))
        }
    }

    suspend fun handleCacheResponseNetworkList(
        cache: BaseCache<S>,
        customOperation: suspend () -> List<T>?,
        savedCache: (List<T>?) -> List<String>,
        operation: (suspend () -> NetworkResult<SafeNetworkResult<List<T>>>)?
    ): NetworkResult<SafeNetworkResult<List<T>>> {
        requestCount += 1
        return try {
            when (networkState) {
                NetworkStateType.CONNECTED -> {
                    var customCache = customOperation.invoke()
                    val network = operation?.invoke()

                    Log.d(CACHE_TAG, "Custom: $customCache")
                    if (customCache.isNullOrEmpty()) {
                        throw IllegalStateException()
                    }

                    if (network is NetworkResult.Success
                        && network.data.data?.equals(customCache) == false
                    ) {
                        cache.collectCacheList(savedCache.invoke(network.data.data))
                        customCache = network.data.data
                    }

                    NetworkResult
                        .Success(SafeNetworkResult(CrudModel(200, SUCCESS), customCache))

                }

                NetworkStateType.NOT_STABLE -> {
                    val customCache =
                        customOperation.invoke() ?: throw NullPointerException(NULL_PROPERTY)
                    NetworkResult
                        .Success(SafeNetworkResult(CrudModel(200, SUCCESS), customCache))
                }
            }

        } catch (illegalE: IllegalStateException) {
            val result = operation?.invoke()
            if (result is NetworkResult.Success && result.data.data != null) {
                val saved = savedCache.invoke(result.data.data)
                cache.collectCacheList(saved)
            }

            Log.d(CACHE_TAG, "Result: $result")
            result ?: NetworkResult.Exception(Throwable(illegalE.message, illegalE.cause))
        } catch (e: Exception) {
            val result = operation?.invoke()
            if (result is NetworkResult.Success && result.data.data != null) {
                cache.collectCacheList(savedCache.invoke(result.data.data))
            }

            Log.e(CACHE_TAG, "Exception: ${e.localizedMessage}")
            Log.d(CACHE_TAG, "Result: $result")
            result ?: NetworkResult.Exception(Throwable(e.message, e.cause))
        }
    }

}