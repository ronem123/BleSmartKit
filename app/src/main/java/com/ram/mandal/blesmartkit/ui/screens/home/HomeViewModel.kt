package com.ram.mandal.blesmartkit.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ram.mandal.blesmartkit.core.dispatcher.DispatcherProvider
import com.ram.mandal.blesmartkit.core.logger.Logger
import com.ram.mandal.blesmartkit.core.networkhelper.NetworkHelper
import com.ram.mandal.blesmartkit.data.repository.NepalTrialRepository
import com.ram.mandal.blesmartkit.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Ram Mandal on 25/01/2024
 * @System: Apple M1 Pro
 */
@SuppressLint("MissingPermission")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Application,
    private val repository: NepalTrialRepository,
    private val networkHelper: NetworkHelper,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {


    private val _bleDevices = MutableStateFlow<UIState<List<ScanResult>>>(UIState.Empty)
    val bleDevices: StateFlow<UIState<List<ScanResult>>> = _bleDevices

    private val _blePairedDevices = MutableStateFlow<UIState<List<BluetoothDevice>>>(UIState.Empty)
    val blePairedDevices: StateFlow<UIState<List<BluetoothDevice>>> = _blePairedDevices

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    private val _totalDevices = MutableStateFlow(0)
    val totalDevices: StateFlow<Int> = _totalDevices

//    private val _deviceInfo = MutableStateFlow(BleDeviceInfo())
//    val deviceInfo: StateFlow<BleDeviceInfo> = _deviceInfo


    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private val bleScanner = bluetoothAdapter?.bluetoothLeScanner


//    private var bluetoothGatt: BluetoothGatt? = null
//    private val _connectionState = MutableStateFlow<Int>(BluetoothProfile.STATE_DISCONNECTED)
//    val connectionState: StateFlow<Int> = _connectionState

    private val bleScanCallback = object : ScanCallback() {

        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result?.let {
                viewModelScope.launch {
                    val currentList = (_bleDevices.value as? UIState.Success)?.data?.toMutableList()
                        ?: mutableListOf()

                    val currentIndex = currentList.indexOfFirst { it ->
                        it.device.address == result.device.address
                    }

                    it.let { scanResult ->
                        if (!currentList.contains(scanResult)) {
                            //update ble device if new found due to rssi update
                            //let say there is device with mac : 12:23:23:21 and rssi 1
                            // later due to rssi updated it will be discovered again so we do not want to
                            //append same device again to the list, instead we update it
                            if (currentIndex >= 0) {
                                currentList[currentIndex] = scanResult
                            } else {
                                currentList.add(scanResult)
                            }
                            _bleDevices.emit(UIState.Success(currentList))
                            _totalDevices.emit(currentList.size)
                        }
                    }
                }
            }

        }

        override fun onScanFailed(errorCode: Int) {

        }
    }
//
//    private val gattCallback = object : BluetoothGattCallback() {
//
//        @SuppressLint("MissingPermission")
//        override fun onConnectionStateChange(
//            gatt: BluetoothGatt,
//            status: Int,
//            newState: Int
//        ) {
//            _connectionState.value = newState
//
//            if (newState == BluetoothProfile.STATE_CONNECTED) {
//                logger.d("BLE", "State-Connected")
//                Log.d("BLE", "Connected to GATT server")
//                gatt.discoverServices()
//            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                Log.d("BLE", "Disconnected from GATT server")
//                bluetoothGatt?.close()
//                bluetoothGatt = null
//            }
//        }
//
//        override fun onServicesDiscovered(
//            gatt: BluetoothGatt,
//            status: Int
//        ) {
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                gatt.services.forEach { service ->
//                    Log.d("BLE", "Service discovered")
//                    onServicesReady(gatt)
//                }
//            } else {
//                Log.d("BLE", "Service discovered Failed")
//            }
//        }
//
//        override fun onCharacteristicRead(
//            gatt: BluetoothGatt,
//            characteristic: BluetoothGattCharacteristic,
//            status: Int
//        ) {
//            when (characteristic.uuid) {
//                BleUuids.BATTERY_CHAR -> handleBattery(characteristic)
//                BleUuids.MANUFACTURER_CHAR,
//                BleUuids.MODEL_CHAR,
//                BleUuids.SERIAL_CHAR -> handleDeviceInfo(characteristic)
//            }
//        }
//
//        override fun onCharacteristicChanged(
//            gatt: BluetoothGatt,
//            characteristic: BluetoothGattCharacteristic
//        ) {
//            when (characteristic.uuid) {
//                BleUuids.HEART_RATE_CHAR -> handleHeartRate(characteristic)
//            }
//        }
//
//    }
//
//    private fun onServicesReady(gatt: BluetoothGatt) {
//        readBattery(gatt)
//        readDeviceInfo(gatt)
//        enableHeartRateNotifications(gatt)
//    }
//
//    private fun readBattery(gatt: BluetoothGatt) {
//        val service = gatt.getService(BleUuids.BATTERY_SERVICE) ?: return
//        val characteristic = service.getCharacteristic(BleUuids.BATTERY_CHAR)
//        gatt.readCharacteristic(characteristic)
//    }
//
//    private fun handleBattery(characteristic: BluetoothGattCharacteristic) {
//        val battery = characteristic.value[0].toInt()
//
//        _deviceInfo.update {
//            it.copy(batteryLevel = battery)
//        }
//    }
//
//    private fun readDeviceInfo(gatt: BluetoothGatt) {
//        val service = gatt.getService(BleUuids.DEVICE_INFO_SERVICE) ?: return
//
//        listOf(
//            BleUuids.MANUFACTURER_CHAR,
//            BleUuids.MODEL_CHAR,
//            BleUuids.SERIAL_CHAR
//        ).forEach { uuid ->
//            service.getCharacteristic(uuid)?.let {
//                gatt.readCharacteristic(it)
//            }
//        }
//    }
//
//    private fun handleDeviceInfo(characteristic: BluetoothGattCharacteristic) {
//        val value = characteristic.getStringValue(0)
//
//        _deviceInfo.update {
//            when (characteristic.uuid) {
//                BleUuids.MANUFACTURER_CHAR ->
//                    it.copy(manufacturer = value)
//
//                BleUuids.MODEL_CHAR ->
//                    it.copy(model = value)
//
//                BleUuids.SERIAL_CHAR ->
//                    it.copy(serialNumber = value)
//
//                else -> it
//            }
//        }
//    }
//
//    private fun enableHeartRateNotifications(gatt: BluetoothGatt) {
//        val service = gatt.getService(BleUuids.HEART_RATE_SERVICE) ?: return
//        val characteristic = service.getCharacteristic(BleUuids.HEART_RATE_CHAR)
//
//        gatt.setCharacteristicNotification(characteristic, true)
//
//        val ccd = characteristic.getDescriptor(
//            UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
//        )
//        ccd.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
//        gatt.writeDescriptor(ccd)
//    }
//
//    private fun handleHeartRate(characteristic: BluetoothGattCharacteristic) {
//        val flags = characteristic.value[0].toInt()
//        val is16Bit = flags and 0x01 != 0
//
//        val heartRate =
//            if (is16Bit)
//                characteristic.getIntValue(
//                    BluetoothGattCharacteristic.FORMAT_UINT16, 1
//                )
//            else
//                characteristic.getIntValue(
//                    BluetoothGattCharacteristic.FORMAT_UINT8, 1
//                )
//
//        _deviceInfo.update {
//            it.copy(heartRate = heartRate)
//        }
//    }


    private fun hasBlePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    @SuppressLint("MissingPermission")
    fun startScan() {
        if (_isScanning.value) return

        if (!hasBlePermission()) return
        bleScanner?.startScan(bleScanCallback)
        _isScanning.value = true
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        Log.v("ViewModel", "Stopped scanning...")
        bleScanner?.stopScan(bleScanCallback)
        viewModelScope.launch {
            _isScanning.emit(false)
        }
    }

    @SuppressLint("MissingPermission")
    fun getDeviceName(bluetoothDevice: BluetoothDevice): String {
        return bluetoothDevice.name ?: "UN_KNOWN"
    }

    fun getDeviceAddress(bluetoothDevice: BluetoothDevice): String {
        return bluetoothDevice.address
    }

    @SuppressLint("MissingPermission")
    fun getDeviceType(bluetoothDevice: BluetoothDevice): Int {
        return bluetoothDevice.type
    }

    @SuppressLint("MissingPermission")
    fun getDeviceBondState(bluetoothDevice: BluetoothDevice): Int {
        return bluetoothDevice.bondState
    }

    // Reset BLE state before retry scan
    fun bleDevicesReset() {
        viewModelScope.launch { _bleDevices.emit(UIState.Empty) }
    }

    fun isBluetoothEnabled(): Boolean {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter?.isEnabled ?: false
    }

    fun enableBluetooth() {

    }

    fun disableBlueTooth() {

    }

    fun isLocationEnabled(): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
    }


    fun enableLocation() {

    }

    @SuppressLint("MissingPermission")
    fun isDeviceBonded(device: BluetoothDevice): Boolean {
        return device.bondState == BluetoothDevice.BOND_BONDED
    }

    @SuppressLint("MissingPermission")
    fun bondDevice(device: BluetoothDevice) {
        if (device.bondState == BluetoothDevice.BOND_NONE) {
            device.createBond()
        }
    }

    @SuppressLint("MissingPermission")
    fun onDeviceBonded(device: BluetoothDevice) {
        logger.d("HomeViewModel", "Device bonded successfully with ${device.name}")
    }

    @SuppressLint("MissingPermission")
    fun onDeviceBondFailed(device: BluetoothDevice) {
        logger.d("HomeViewModel", "Device bonded failed for ${device.name}")
    }

    @SuppressLint("MissingPermission")
    fun getBondedDevices() {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        viewModelScope.launch {
            _blePairedDevices.emit(
                UIState.Success(
                    bluetoothManager.adapter?.bondedDevices?.toList() ?: emptyList()
                )
            )
        }
    }

//    @SuppressLint("MissingPermission")
//    fun connectToDevice(device: BluetoothDevice) {
//        device.connectGatt(
//            context,
//            false, // autoConnect = false (IMPORTANT)
//            gattCallback
//        )
//    }

}





