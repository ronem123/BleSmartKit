package com.ram.mandal.blesmartkit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ram.mandal.blesmartkit.R
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor


/**
 * Created by Ram Mandal on 30/01/2024
 * @System: Apple M1 Pro
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonToolbar(onBackClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppThemeColor.primary,
            titleContentColor = AppThemeColor.white,
        ),
        navigationIcon = {
            IconButton(onClick = {
                onBackClicked()
            }) {
                Icon(
                    tint = AppThemeColor.white,
                    imageVector = Icons.Filled.ArrowBack, contentDescription = null
                )
            }
        })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeToolbar(onHamburgerIconClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppThemeColor.primary,
            titleContentColor = AppThemeColor.white,
        ),
        navigationIcon = {
            IconButton(onClick = { onHamburgerIconClicked() }) {
                Icon(
                    tint = AppThemeColor.white,
                    imageVector = Icons.Filled.Menu, contentDescription = null
                )
            }
        })

}
