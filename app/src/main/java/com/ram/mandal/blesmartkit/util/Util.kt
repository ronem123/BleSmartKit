package com.ram.mandal.blesmartkit.util

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat

object MyIntent {
    val video = "video_key"
    val radio = "radio_key"
    val route = "route_key"
    val menu_item = "menu_item"
    val navigationDrawer = "navigation_key"

}

inline fun <reified T : Parcelable> getBundleObject(bundle: Bundle?, key: String): T? {
    return if (Build.VERSION.SDK_INT >= 33) bundle?.getParcelable(key, T::class.java)
    else bundle?.getParcelable(key)
}


@SuppressLint("MissingPermission")
fun getBleDeviceType(context: Context, device: BluetoothDevice): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH_CONNECT
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        "Unknown"
    } else {
        device.name ?: "Unknown"
    }
}

fun bleType(type: Int): String {
    return when (type) {
        BluetoothDevice.DEVICE_TYPE_LE -> "BLE only"
        BluetoothDevice.DEVICE_TYPE_DUAL -> "Classic + BLE"
        BluetoothDevice.DEVICE_TYPE_CLASSIC -> "Classic only"
        else -> "Unknown"
    }
}

fun bleBondState(bondState: Int): String {
    return when (bondState) {
        BluetoothDevice.BOND_NONE -> "Not Paired"
        BluetoothDevice.BOND_BONDING -> "Pairing..."
        BluetoothDevice.BOND_BONDED -> "Paired"
        else -> "Unknown"
    }
}
