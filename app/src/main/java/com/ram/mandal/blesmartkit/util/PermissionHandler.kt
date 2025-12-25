package com.ram.mandal.blesmartkit.util

import android.Manifest
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

/**
 * Created by Ram Mandal on 15/10/2025
 * @System: Apple M1 Pro
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestAppPermissions(
    permissions: List<String>,
    rationaleMessage: String,
    onAllPermissionsGranted: () -> Unit
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)

    //  Automatically request permissions when composable loads
    LaunchedEffect(Unit) {
        multiplePermissionsState.launchMultiplePermissionRequest()
    }

    when {
        multiplePermissionsState.allPermissionsGranted -> {
            onAllPermissionsGranted()
        }

        multiplePermissionsState.shouldShowRationale -> {
            PermissionRationaleUI(rationaleMessage) {
                multiplePermissionsState.launchMultiplePermissionRequest()
            }
        }

        else -> {
            PermissionRequestUI {
                multiplePermissionsState.launchMultiplePermissionRequest()
            }
        }
    }
}

@Composable
fun PermissionRationaleUI(
    rationaleMessage: String,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Permissions Needed") },
        text = { Text(rationaleMessage) },
        confirmButton = {
            TextButton(onClick = onRequestPermission) {
                Text("Grant Now")
            }
        }
    )
}

@Composable
fun PermissionRequestUI(onRequestPermission: () -> Unit) {
    Button(onClick = onRequestPermission) {
        Text("Allow Permission")
    }
}
