package com.ram.mandal.blesmartkit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor


/**
 * Created by Ram Mandal on 23/01/2024
 * @System: Apple M1 Pro
 */

@Composable
fun ButtonComponents(
    buttonText: String,
    buttonBgColor: Color = AppThemeColor.grey20,
    color: Color = AppThemeColor.black,
    height: Dp = 40.dp,
    horizontalPadding: Dp = 15.dp,
    verticalPadding: Dp = 5.dp,
    typography: TextStyle = MaterialTheme.typography.bodySmall,
    modifier: Modifier,
    corner: Int = 2,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .height(height)

    ) {
        Button(
            onClick = { onClick.invoke() },
            colors = ButtonDefaults.buttonColors(containerColor = buttonBgColor),
            modifier = Modifier.padding(0.dp),
            shape = RoundedCornerShape(corner)
        ) {
            TextComponents(text = buttonText, color = color, typography = typography)
        }

    }
}
