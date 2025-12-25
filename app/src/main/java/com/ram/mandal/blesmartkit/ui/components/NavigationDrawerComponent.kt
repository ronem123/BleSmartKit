package com.ram.mandal.blesmartkit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ram.mandal.blesmartkit.R
import com.ram.mandal.blesmartkit.data.model.NavigationItem
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor
import androidx.compose.foundation.lazy.items


/**
 * Created by Ram Mandal on 24/01/2024
 * @System: Apple M1 Pro
 */
@Composable
fun NavigationDrawerBody(
    navigationItems: List<NavigationItem>,
    onNavigationItemClicked: (NavigationItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(navigationItems) {
            NavigationItemRow(it, onNavigationItemClicked)
        }
    }
}


@Composable
fun NavigationDrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = AppThemeColor.white),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Navigation Header",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun NavigationItemRow(item: NavigationItem, onNavigationItemClicked: (NavigationItem) -> Unit) {
    NavigationDrawerItem(
        icon = {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = item.contentDesc,
                modifier = Modifier
                    .width(34.dp)
                    .height(34.dp)
            )
        },
        label = { TextComponents(text = item.title, typography = MaterialTheme.typography.bodyMedium) },
        onClick = {
            onNavigationItemClicked(item)
        },
        selected = false,
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}
