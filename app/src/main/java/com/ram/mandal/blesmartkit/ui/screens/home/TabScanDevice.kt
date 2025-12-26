package com.ram.mandal.blesmartkit.ui.screens.home

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ram.mandal.blesmartkit.ui.UIState
import com.ram.mandal.blesmartkit.ui.components.ButtonComponents
import com.ram.mandal.blesmartkit.ui.components.ErrorComposableLayout
import com.ram.mandal.blesmartkit.ui.components.LoadingComposeLayout
import com.ram.mandal.blesmartkit.ui.components.TextComponents
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor
import com.ram.mandal.blesmartkit.util.RequestAppPermissions


/**
 * Created by Ram Mandal on 26/12/2025
 * @System: Apple M1 Pro
 */
@Composable
fun TabScanDevice(viewModel: HomeViewModel, context: Context) {
    val bleState: UIState<List<ScanResult>> by viewModel.bleDevices.collectAsStateWithLifecycle()
    val isScanningState by viewModel.isScanning.collectAsStateWithLifecycle()
    val totalDevices by viewModel.totalDevices.collectAsStateWithLifecycle()

    var requestEnableBluetooth by rememberSaveable { mutableStateOf(false) }
    var bluetoothEnableRequested by rememberSaveable { mutableStateOf(false) }

    var requestEnableGPS by rememberSaveable { mutableStateOf(false) }
    var gpsEnableRequested by rememberSaveable { mutableStateOf(false) }

    val enableBluetoothLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            bluetoothEnableRequested = false
        }

    val enableGpsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        gpsEnableRequested = false
    }


    val blePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    } else {
        listOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(requestEnableBluetooth) {
        if (requestEnableBluetooth) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            enableBluetoothLauncher.launch(intent)
            requestEnableBluetooth = false
        }
    }

    LaunchedEffect(requestEnableGPS) {
        if (requestEnableGPS) {
            val gpsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            enableGpsLauncher.launch(gpsIntent)
            requestEnableGPS = false
        }
    }


    RequestAppPermissions(
        permissions = blePermissions, rationaleMessage = "Rationale message"
    ) {
        if (!viewModel.isBluetoothEnabled() && !bluetoothEnableRequested) {
            bluetoothEnableRequested = true
            requestEnableBluetooth = true
        } else if (!viewModel.isLocationEnabled() && !gpsEnableRequested) {
            requestEnableGPS = true
            gpsEnableRequested = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppThemeColor.white)
    ) {
        //Scan/stop button
        Row {
            TextComponents(
                text = "Found $totalDevices Devices",
                modifier = Modifier.padding(4.dp, 8.dp),
                color = AppThemeColor.black,
                typography = MaterialTheme.typography.bodyMedium
            )
            ButtonComponents(
                modifier = Modifier.padding(0.dp),
                color = AppThemeColor.white,
                buttonText = if (isScanningState) "Stop scan" else "Start scan",
                buttonBgColor = AppThemeColor.primary,
                height = 30.dp,
                corner = 10,
                verticalPadding = 0.dp
            ) {
                if (isScanningState) {
                    viewModel.stopScan()
                } else {
                    viewModel.startScan()
                }
            }
            if (isScanningState) LoadingComposeLayout(
                boxModifier = Modifier
                    .width(30.dp)
                    .height(30.dp),
                progressModifier = Modifier.padding(5.dp)
            )
        }

        when (bleState) {
            is UIState.Loading -> {}

            is UIState.Success -> {
                val scanResults = (bleState as UIState.Success<List<ScanResult>>).data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppThemeColor.white)
                ) {
                    item {
                        TextComponents(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = "Discovered devices",
                            color = AppThemeColor.grey40,
                            typography = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                    items(scanResults) { scanResult ->
                        BleCard(viewModel, scanResult = scanResult, onItemClick = {
                            if (!viewModel.isDeviceBonded(scanResult.device)) {
                                viewModel.bondDevice(device = scanResult.device)
                            }
                        }, {
                            if (!viewModel.isDeviceBonded(scanResult.device)) {
                                viewModel.bondDevice(device = scanResult.device)
                            }
                        })
                    }

                }
            }

            is UIState.Failure -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ErrorComposableLayout(
                        errorMessage = (bleState as UIState.Failure).throwable?.message
                            ?: "Scan failed"
                    )
                }
            }

            is UIState.Empty -> {
                Log.v("TAG", "Empty")

            }
        }

    }
}