package com.ram.mandal.blesmartkit.ui.screens.home

import android.bluetooth.le.ScanResult
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ram.mandal.blesmartkit.ui.UIState
import com.ram.mandal.blesmartkit.ui.components.TextComponents
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor


/**
 * Created by Ram Mandal on 26/12/2025
 * @System: Apple M1 Pro
 */
@Composable
fun TabPairedDeviceTab(viewModel: HomeViewModel, context: Context) {
    val pairedDeviceState: UIState<List<ScanResult>> by viewModel.blePairedDevices.collectAsStateWithLifecycle()
    Column {
        when (pairedDeviceState) {
            is UIState.Success -> {
                val scanResults = (pairedDeviceState as UIState.Success<List<ScanResult>>).data
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
                            text = "Paired devices",
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
                            val device = scanResult.device
                            Toast.makeText(
                                context,
                                "connect: ${viewModel.getDeviceName(bluetoothDevice = device)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    }

                }
            }

            UIState.Empty -> {}
            is UIState.Failure<*> -> {}
            UIState.Loading -> {}
        }
    }
}