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
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ram.mandal.blesmartkit.core.dispatcher.DispatcherProvider
import com.ram.mandal.blesmartkit.core.logger.Logger
import com.ram.mandal.blesmartkit.core.networkhelper.NetworkHelper
import com.ram.mandal.blesmartkit.data.model.DiscoveredBleDevice
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
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Application,
    private val repository: NepalTrialRepository,
    private val networkHelper: NetworkHelper,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {


    private val _bleDevices = MutableStateFlow<UIState<List<DiscoveredBleDevice>>>(UIState.Empty)
    val bleDevices: StateFlow<UIState<List<DiscoveredBleDevice>>> = _bleDevices

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private val bleScanner = bluetoothAdapter?.bluetoothLeScanner

    private val bleScanCallback = object : ScanCallback() {

        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result?.let {
                viewModelScope.launch {
                    val currentList = (_bleDevices.value as? UIState.Success)?.data?.toMutableList()
                        ?: mutableListOf()

                    val currentIndex = currentList.indexOfFirst { it ->
                        it.address == result.device.address
                    }

                    it.device?.let { device ->
                        val bleDevice = DiscoveredBleDevice(
                            name = device.name ?: "Unknown",
                            type = device.type,
                            address = device.address,
                            bondState = device.bondState,
                            rssi = result.rssi,
                            txPower = result.txPower,
                            serviceUUIDs = result.scanRecord?.serviceUuids?.map { sId -> sId.uuid.toString() }
                                ?: emptyList()
                        )
                        if (!currentList.contains(bleDevice)) {
                            //update ble device if new found due to rssi update
                            //let say there is device with mac : 12:23:23:21 and rssi 1
                            // later due to rssi updated it will be discovered again so we do not want to
                            //append same device again to the list, instead we update it
                            if (currentIndex >= 0) {
                                currentList[currentIndex] = bleDevice
                            } else {
                                currentList.add(bleDevice)
                            }
                            _bleDevices.emit(UIState.Success(currentList))
                        }
                    }
                }
            }

        }

        override fun onScanFailed(errorCode: Int) {
//            viewModelScope.launch {
//                if (_bleDevices.value !is UIState.Failure) {
//                    _bleDevices.emit(UIState.Failure(Throwable("can't scan")))
//                }
//            }
        }
    }

    private fun hasBlePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) ==
                    PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan() {
        if (!hasBlePermission()) return
//        if (_bleDevices.value !is UIState.Loading) {
//            viewModelScope.launch {
//                _bleDevices.emit(UIState.Loading)
//            }
//        }
        bleScanner?.startScan(bleScanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        bleScanner?.stopScan(bleScanCallback)
    }

    // Reset BLE state before retry scan
    fun bleDevicesReset() {
        viewModelScope.launch { _bleDevices.emit(UIState.Empty) }
    }

}





