package com.ram.mandal.blesmartkit.ui.screens.devicedetails

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ram.mandal.blesmartkit.core.dispatcher.DispatcherProvider
import com.ram.mandal.blesmartkit.core.logger.Logger
import com.ram.mandal.blesmartkit.core.networkhelper.NetworkHelper
import com.ram.mandal.blesmartkit.data.BleUuids
import com.ram.mandal.blesmartkit.data.model.BleDeviceInfo
import com.ram.mandal.blesmartkit.data.repository.NepalTrialRepository
import com.ram.mandal.blesmartkit.ui.DeviceScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue
import java.util.UUID
import javax.inject.Inject


/**
 * Created by Ram Mandal on 31/12/2025
 * @System: Apple M1 Pro
 */
@SuppressLint("MissingPermission")
@HiltViewModel
class DeviceDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: NepalTrialRepository,
    private val networkHelper: NetworkHelper,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val characteristicQueue: Queue<BluetoothGattCharacteristic> = LinkedList()
    private val deviceName = savedStateHandle.get<String>("name") ?: "Unknown"
    private val address =
        savedStateHandle.get<String>("address") ?: error("Device address is missing")

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private var bluetoothGatt: BluetoothGatt? = null


    private val _connectionState = MutableStateFlow<Int>(BluetoothProfile.STATE_DISCONNECTED)
    val connectionState: StateFlow<Int> = _connectionState

    private val _uiState = MutableStateFlow<DeviceScreenState>(DeviceScreenState.Idle)
    val uiState: StateFlow<DeviceScreenState> = _uiState

    private val _deviceDetail = MutableStateFlow(BleDeviceInfo())
    val deviceDetail: StateFlow<BleDeviceInfo> = _deviceDetail


    fun initiateConnection() {
        val device = bluetoothAdapter.getRemoteDevice(address)
        Log.v("DetailViewModel", "Add:${device.address} - Name:${device.name}")
        viewModelScope.launch { _uiState.emit(DeviceScreenState.Connecting) }
        connectToDevice(device)

    }

    private val gattCallback = object : BluetoothGattCallback() {

        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            _connectionState.value = newState

            if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
                viewModelScope.launch { _uiState.emit(DeviceScreenState.Connected) }
                gatt.discoverServices()
                logger.d("DeviceDetailViewModel", "GAT SUCCESS")
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                logger.d("DeviceDetailViewModel", "GAT DISCONNECTED")
                viewModelScope.launch { _uiState.emit(DeviceScreenState.Error("Disconnected from GAT server")) }
                bluetoothGatt?.close()
                bluetoothGatt = null
            }
        }

        override fun onServicesDiscovered(
            gatt: BluetoothGatt,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                logger.d("DeviceDetailViewModel", "GAT SUCCESS: ServiceDiscovered")
                onServicesReady(gatt)
            } else {
                viewModelScope.launch { _uiState.emit(DeviceScreenState.Error("No service discovered")) }
                logger.d("DeviceDetailViewModel", "GAT Failed: ServiceDiscovered")
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            logger.d("DeviceDetailViewModel", "onCharacteristicRead")
            when (characteristic.uuid) {
                BleUuids.BATTERY_CHAR -> handleBattery(characteristic)
                BleUuids.MANUFACTURER_CHAR,
                BleUuids.MODEL_CHAR,
                BleUuids.SERIAL_CHAR -> handleDeviceInfo(characteristic)
            }
            // trigger next read in queue
            readNextCharacteristic(gatt)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            when (characteristic.uuid) {
                BleUuids.HEART_RATE_CHAR -> handleHeartRate(characteristic)
            }
        }

    }

    private fun onServicesReady(gatt: BluetoothGatt) {
        val batteryChar = gatt.getService(BleUuids.BATTERY_SERVICE)?.getCharacteristic(BleUuids.BATTERY_CHAR)
        val deviceInfoService = gatt.getService(BleUuids.DEVICE_INFO_SERVICE)
        val deviceChars = listOfNotNull(
            deviceInfoService?.getCharacteristic(BleUuids.MANUFACTURER_CHAR),
            deviceInfoService?.getCharacteristic(BleUuids.MODEL_CHAR),
            deviceInfoService?.getCharacteristic(BleUuids.SERIAL_CHAR)
        )

        val allChars = mutableListOf<BluetoothGattCharacteristic>()
        batteryChar?.let { allChars.add(it) }
        allChars.addAll(deviceChars)

        readCharacteristicsSequentially(gatt, allChars)

        enableHeartRateNotifications(gatt)
    }


    private fun readCharacteristicsSequentially(gatt: BluetoothGatt, characteristics: List<BluetoothGattCharacteristic>) {
        characteristicQueue.clear()
        characteristicQueue.addAll(characteristics)
        readNextCharacteristic(gatt)
    }

    private fun readNextCharacteristic(gatt: BluetoothGatt) {
        val next = characteristicQueue.poll() ?: return // no more
        gatt.readCharacteristic(next)
    }

    private fun readBattery(gatt: BluetoothGatt) {
        val service = gatt.getService(BleUuids.BATTERY_SERVICE) ?: return
        val characteristic = service.getCharacteristic(BleUuids.BATTERY_CHAR)
        gatt.readCharacteristic(characteristic)
    }

    private fun handleBattery(characteristic: BluetoothGattCharacteristic) {
        val battery = characteristic.value[0].toInt()

        _deviceDetail.update { it.copy(batteryLevel = battery) }
        logger.d("DeviceDetailViewModel", "Handle Battery : Percentage-$battery")

    }

    private fun readDeviceInfo(gatt: BluetoothGatt) {
        val service = gatt.getService(BleUuids.DEVICE_INFO_SERVICE) ?: return

        listOf(
            BleUuids.MANUFACTURER_CHAR,
            BleUuids.MODEL_CHAR,
            BleUuids.SERIAL_CHAR
        ).forEach { uuid ->
            service.getCharacteristic(uuid)?.let {
                gatt.readCharacteristic(it)
            }
            logger.d("DeviceDetailViewModel", "Reading device Info")
        }
    }

    private fun handleDeviceInfo(characteristic: BluetoothGattCharacteristic) {
        val value = characteristic.getStringValue(0)

        _deviceDetail.update {
            when (characteristic.uuid) {
                BleUuids.MANUFACTURER_CHAR ->
                    it.copy(manufacturer = value)

                BleUuids.MODEL_CHAR ->
                    it.copy(model = value)

                BleUuids.SERIAL_CHAR ->
                    it.copy(serialNumber = value)

                else -> it
            }
        }
        logger.d("DeviceDetailViewModel", "Handle DeviceInfo ::$value")
    }

    private fun enableHeartRateNotifications(gatt: BluetoothGatt) {
        val service = gatt.getService(BleUuids.HEART_RATE_SERVICE) ?: return
        val characteristic = service.getCharacteristic(BleUuids.HEART_RATE_CHAR)

        gatt.setCharacteristicNotification(characteristic, true)

        val ccd = characteristic.getDescriptor(
            UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
        )
        ccd.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        gatt.writeDescriptor(ccd)
    }

    private fun handleHeartRate(characteristic: BluetoothGattCharacteristic) {
        val flags = characteristic.value[0].toInt()
        val is16Bit = flags and 0x01 != 0

        val heartRate =
            if (is16Bit) {
                characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 1)
            } else {
                characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 1)
            }
        _deviceDetail.update {
            it.copy(heartRate = heartRate)
        }
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        bluetoothGatt =
            device.connectGatt(
                context,
                false,
                gattCallback,
                BluetoothDevice.TRANSPORT_LE
            )
    }


    override fun onCleared() {
        super.onCleared()
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
}
