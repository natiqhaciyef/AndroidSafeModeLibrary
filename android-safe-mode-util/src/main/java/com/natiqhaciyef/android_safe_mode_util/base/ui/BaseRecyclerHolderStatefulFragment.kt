package com.natiqhaciyef.android_safe_mode_util.base.ui

import androidx.viewbinding.ViewBinding


abstract class BaseRecyclerHolderStatefulFragment<VB : ViewBinding,
        VM : BaseViewModel<State, Event, Effect>,
        T: Any,
        RV : BaseRecyclerViewAdapter<T, *>,
        State : UiState,
        Event : UiEvent,
        Effect : UiEffect> :
    BaseFragment<VB, VM, State, Event, Effect>() {
    abstract var adapter: RV?
    abstract fun recyclerViewConfig(list: List<T>)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}