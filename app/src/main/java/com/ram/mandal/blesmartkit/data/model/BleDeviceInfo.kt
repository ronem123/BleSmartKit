package com.ram.mandal.blesmartkit.data.model


/**
 * Created by Ram Mandal on 31/12/2025
 * @System: Apple M1 Pro
 */
data class BleDeviceInfo(
    val deviceName: String? = null,
    val macAddress: String? = null,

    // Battery
    val batteryLevel: Int? = null,

    // Device info
    val manufacturer: String? = null,
    val model: String? = null,
    val serialNumber: String? = null,

    // Heart Rate
    val heartRate: Int? = null
)
