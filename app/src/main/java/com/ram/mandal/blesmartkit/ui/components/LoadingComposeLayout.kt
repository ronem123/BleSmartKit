package com.ram.mandal.blesmartkit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.ram.mandal.blesmartkit.R


/**
 * Created by Ram Mandal on 18/02/2024
 * @System: Apple M1 Pro
 */

@Composable
fun LoadingComposeLayout(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val contentDesc = stringResource(R.string.loading)
        CircularProgressIndicator(
            modifier = modifier
                .align(Alignment.Center)
                .semantics {
                    contentDescription = contentDesc
                }
        )
    }
}


@Composable
@Preview
fun PreviewLoading() {
    LoadingComposeLayout()
}