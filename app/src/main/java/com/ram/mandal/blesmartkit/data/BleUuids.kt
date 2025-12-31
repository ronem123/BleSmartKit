package com.ram.mandal.blesmartkit.data

import java.util.UUID


/**
 * Created by Ram Mandal on 31/12/2025
 * @System: Apple M1 Pro
 */
object BleUuids {

    val BATTERY_SERVICE =
        UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb")
    val BATTERY_CHAR =
        UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")

    val DEVICE_INFO_SERVICE =
        UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb")
    val MANUFACTURER_CHAR =
        UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb")
    val MODEL_CHAR =
        UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb")
    val SERIAL_CHAR =
        UUID.fromString("00002a25-0000-1000-8000-00805f9b34fb")

    val HEART_RATE_SERVICE =
        UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb")
    val HEART_RATE_CHAR =
        UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb")
}
