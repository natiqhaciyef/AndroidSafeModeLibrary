package com.natiqhaciyef.android_safe_mode_util.base.ui


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natiqhaciyef.android_safe_mode_util.models.io.SafeResult
import com.natiqhaciyef.android_safe_mode_util.models.io.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel(),
    CoroutineScope {
    private val job = Job()

    private val currentState: State by lazy { getInitialState() }
    private var _state = MutableStateFlow(currentState)
    val state: StateFlow<State> = _state
    val stateValue = _state.value
    private val holdState: State
        get() = _state.value

    private var _event = MutableSharedFlow<Event>()
    private val event: SharedFlow<Event> = _event

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()
    private var eventJob: Job? = null

    init {
        initEventSubscribers()
    }

    private fun initEventSubscribers() {
        eventJob = event.onEach {
            onEventUpdate(it)
        }.launchIn(viewModelScope)
    }


    protected open fun onEventUpdate(event: Event) {}


    abstract fun getInitialState(): State

    protected fun setBaseState(state: State) {
        viewModelScope.launch { _state.emit(state) }
    }

    fun getHoldState(): State {
        return holdState
    }

    fun holdBaseState(state: State) {
        _state.value = state
    }

    fun getCurrentBaseState(): State {
        return currentState
    }

    fun postEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    suspend fun updateEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    fun postEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    protected fun <IN> operationHandler(
        result: SafeResult<IN>,
        isHold: Boolean = false,
        isCleared: Boolean = false,
        tag: String = "LETS_YOU_IN",
        onErrorEvent: (() -> State)? = null,
        onLoadingEvent: (() -> State)? = null,
        onVoidEvent: (() -> Unit)? = null,
        onSuccessEvent: (IN?) -> State
    ) {
        when (result.status) {
            Status.SUCCESS -> {
                if (isCleared)
                    clearState()
                val confirmedState = onSuccessEvent.invoke(result.data)
                confirmedState.isLoading = false

                setBaseState(confirmedState)
                Log.d("${tag}_BASE_VM", "${state.value}")
            }

            Status.ERROR -> {
                if (onErrorEvent != null) {
                    setBaseState(onErrorEvent.invoke())
                } else {
                    val confirmedState =
                        if (isHold) getHoldState() else getInitialState()
                    confirmedState.isLoading = false

                    setBaseState(confirmedState)
                }
                Log.e("${tag}_BASE_VM", "${state.value}")
            }

            Status.LOADING -> {
                if (onLoadingEvent != null) {
                    setBaseState(onLoadingEvent.invoke())
                } else {
                    val confirmedState =
                        if (isHold) getHoldState() else getInitialState()
                    confirmedState.isLoading = true

                    setBaseState(confirmedState)
                }

                Log.w("${tag}_BASE_VM", "${state.value}")
            }

            Status.VOID -> {
                onVoidEvent?.invoke()
                Log.d("${tag}_BASE_VM", "${state.value}")
            }

            Status.IDLE -> {
                clearState()
                Log.d("${tag}_BASE_VM", "${state.value}")
            }
        }
    }

    private fun clearState() {
        _state.value = getInitialState()
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        eventJob?.cancel()
    }
}