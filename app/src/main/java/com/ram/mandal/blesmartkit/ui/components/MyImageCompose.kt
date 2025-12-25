package com.ram.mandal.blesmartkit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


/**
 * Created by Ram Mandal on 18/02/2024
 * @System: Apple M1 Pro
 */
@Composable
fun MyImageCompose(resId: Int, contentScale: ContentScale = ContentScale.Fit) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = "Error",
        contentScale = contentScale,
        modifier = Modifier.height(100.dp)
    )
}