package com.natiqhaciyef.android_safe_mode_util.base.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected var _binding: VB? = null
    val binding: VB
        get() = _binding!!


}
