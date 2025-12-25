package com.ram.mandal.blesmartkit.ui.screens.home

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ram.mandal.blesmartkit.data.model.DiscoveredBleDevice
import com.ram.mandal.blesmartkit.ui.UIState
import com.ram.mandal.blesmartkit.ui.components.ButtonComponents
import com.ram.mandal.blesmartkit.ui.components.ErrorComposableLayout
import com.ram.mandal.blesmartkit.ui.components.LoadingComposeLayout
import com.ram.mandal.blesmartkit.ui.components.TextComponents
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor
import com.ram.mandal.blesmartkit.util.RequestAppPermissions
import kotlinx.coroutines.launch


/**
 * Created by Ram Mandal on 25/01/2024
 * @System: Apple M1 Pro
 */


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopScan()
        }
    }

    HomeContent(viewModel)
}


@Composable
fun HomeContent(viewModel: HomeViewModel, context: Context = LocalContext.current) {
    val bleState: UIState<List<DiscoveredBleDevice>> by viewModel.bleDevices.collectAsStateWithLifecycle()
    val isScanningState by viewModel.isScanning.collectAsStateWithLifecycle()

    val blePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    } else {
        listOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    RequestAppPermissions(
        permissions = blePermissions,
        rationaleMessage = "Rationale message"
    ) {

    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppThemeColor.white)
    ) {
        //Scan/stop button
        Row {
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
                val devices = (bleState as UIState.Success<List<DiscoveredBleDevice>>).data
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
                    items(devices) { device ->
                        BleCard(device, {
                            Toast.makeText(context,"clicked: ${device.name}", Toast.LENGTH_SHORT).show()
                        }, {
                            Toast.makeText(context,"connect: ${device.name}", Toast.LENGTH_SHORT).show()
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
