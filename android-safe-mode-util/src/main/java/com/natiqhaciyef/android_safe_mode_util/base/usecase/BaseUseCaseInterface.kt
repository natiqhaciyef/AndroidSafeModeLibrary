package com.natiqhaciyef.android_safe_mode_util.base.usecase

import com.natiqhaciyef.android_safe_mode_util.models.io.SafeResult
import kotlinx.coroutines.flow.Flow

interface BaseUseCaseInterface<In, Out> {
    /**
     * The reason of invoke is the adding return param and
     * generic type, which supports to expand type scale.
     *
     * @throws null parameter while data not found
     * @exception NullPointerException
     * @return Flow List of UIResult of K type
     * @author Natig Hajiyev && ProScan app
     */
    operator fun invoke(): Flow<SafeResult<Out>>?


    /***
     * Run function gets single data as a parameter and
     * creates flowable Boolean type. It collects single data.
     *
     * @throws null parameter while data not found
     * @exception NullPointerException
     * @param K type
     * @return Flow SafeResult<Boolean>
     * @author Natig Hajiyev && ProScan app
     */
    fun run(data: In): Flow<SafeResult<Boolean>>


    /***
     * Operate function gets In type single data as a parameter and
     * creates flowable Out type. It collects single data.
     *
     * @throws null parameter while data not found
     * @exception NullPointerException
     * @param In type
     * @return Flow Out type
     * @author Natig Hajiyev && ProScan app
     */
    fun operate(data: In): Flow<SafeResult<Out>>?
}