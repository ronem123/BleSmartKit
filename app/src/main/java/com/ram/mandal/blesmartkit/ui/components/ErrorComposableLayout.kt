package com.ram.mandal.blesmartkit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ram.mandal.blesmartkit.R
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor


/**
 * Created by Ram Mandal on 18/02/2024
 * @System: Apple M1 Pro
 */
@Composable
fun ErrorComposableLayout(errorMessage: String) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppThemeColor.white),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyImageCompose(R.drawable.ic_error_red)
            TextComponents(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                text = errorMessage,
                color = AppThemeColor.grey40,
                typography = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
@Preview
fun PreviewError() {
    ErrorComposableLayout(errorMessage = "Something went wrong.\nPlease try again")
}