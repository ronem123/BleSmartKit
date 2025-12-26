package com.ram.mandal.blesmartkit.ui.effects

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ram.mandal.blesmartkit.ui.screens.home.HomeViewModel


/**
 * Created by Ram Mandal on 26/12/2025
 * @System: Apple M1 Pro
 */
@Composable
fun BondReceiverEffect(viewModel: HomeViewModel) {
    val context = LocalContext.current

    DisposableEffect(Unit) {

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action != BluetoothDevice.ACTION_BOND_STATE_CHANGED) return

                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        ?: return

                when (
                    intent.getIntExtra(
                        BluetoothDevice.EXTRA_BOND_STATE,
                        BluetoothDevice.ERROR
                    )
                ) {
                    BluetoothDevice.BOND_BONDED -> {
                        viewModel.onDeviceBonded(device)
                    }

                    BluetoothDevice.BOND_NONE -> {
                        viewModel.onDeviceBondFailed(device)
                    }
                }
            }
        }

        ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
}
