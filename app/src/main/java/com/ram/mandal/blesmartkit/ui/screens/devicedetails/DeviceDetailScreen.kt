package com.ram.mandal.blesmartkit.ui.screens.devicedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ram.mandal.blesmartkit.data.model.BleDeviceInfo
import com.ram.mandal.blesmartkit.ui.DeviceScreenState
import com.ram.mandal.blesmartkit.ui.components.TextComponents
import com.ram.mandal.blesmartkit.ui.theme.Typography


/**
 * Created by Ram Mandal on 31/12/2025
 * @System: Apple M1 Pro
 */
@Composable
fun DeviceDetailScreen(
    viewModel: DeviceDetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val deviceInfoState by viewModel.deviceDetail.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initiateConnection()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        when (uiState) {
            DeviceScreenState.Connecting -> {
                TextComponents(
                    text = "Connecting...",
                    typography = Typography.bodySmall
                )
            }

            DeviceScreenState.Connected -> {
                TextComponents(
                    text = "Connected",
                    typography = Typography.bodySmall
                )
                DeviceInfoScreen(deviceInfoState)
            }

            is DeviceScreenState.Error -> {
                val message = (uiState as DeviceScreenState.Error).message
                Text("Error Occurred with $message")
            }

            DeviceScreenState.Idle -> Unit
        }
    }
}

@Composable
fun DeviceInfoScreen(deviceInfo: BleDeviceInfo) {
    Column {
        TextComponents(
            text = "Battery: ${deviceInfo.batteryLevel ?: "--"}%",
            typography = Typography.bodyLarge
        )
        TextComponents(
            text = "Manufacturer: ${deviceInfo.manufacturer ?: "--"}",
            typography = Typography.bodySmall
        )
        TextComponents(
            text = "Model: ${deviceInfo.model ?: "--"}",
            typography = Typography.bodySmall
        )
        TextComponents(
            text = "Heart Rate: ${deviceInfo.heartRate ?: "--"} bpm",
            typography = Typography.bodySmall
        )
    }
}