package com.ram.mandal.blesmartkit.ui.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ram.mandal.blesmartkit.ui.effects.BondReceiverEffect
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor


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

    BondReceiverEffect(viewModel)
    HomeContent(viewModel)
}


@Composable
fun HomeContent(viewModel: HomeViewModel, context: Context = LocalContext.current) {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.SCAN) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppThemeColor.white)
    ) {
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = AppThemeColor.white
        ) {
            HomeTab.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab.ordinal == index,
                    onClick = { selectedTab = tab },
                    text = { Text(tab.title) }
                )
            }
        }
        when (selectedTab) {
            HomeTab.SCAN -> TabScanDevice(viewModel, context)
            HomeTab.PAIRED -> TabPairedDeviceTab(viewModel, context)
        }
    }

}
