package com.ram.mandal.blesmartkit.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor


/**
 * Created by Ram Mandal on 23/01/2024
 * @System: Apple M1 Pro
 */
@Composable
fun TextComponents(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = AppThemeColor.black,
    typography: TextStyle
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        color = color,
        style = typography
    )
}

@Composable
fun TextComponentsWithMaxLine(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = AppThemeColor.black,
    typography: TextStyle,
    maxLines: Int = 2,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = typography,
        maxLines = maxLines,
        overflow = overflow
    )
}
