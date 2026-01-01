package com.ram.mandal.blesmartkit.ui


/**
 * Created by Ram Mandal on 31/12/2025
 * @System: Apple M1 Pro
 */
sealed class DeviceScreenState {
    data object Idle : DeviceScreenState()
    data object Connecting : DeviceScreenState()
    data object Connected : DeviceScreenState()
    data class Error(val message: String) : DeviceScreenState()
}