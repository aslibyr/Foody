package com.aslibayar.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NetworkStateHolder {
    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected

    fun updateConnectionStatus(status: Boolean) {
        _isConnected.value = status
    }
}
