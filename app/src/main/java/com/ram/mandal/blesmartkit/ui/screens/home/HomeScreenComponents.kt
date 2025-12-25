package com.ram.mandal.blesmartkit.ui.screens.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ram.mandal.blesmartkit.R
import com.ram.mandal.blesmartkit.data.model.DiscoveredBleDevice
import com.ram.mandal.blesmartkit.data.model.MenuConfig
import com.ram.mandal.blesmartkit.data.model.MyMenuItem
import com.ram.mandal.blesmartkit.data.model.MyVideo
import com.ram.mandal.blesmartkit.data.model.NewsItem
import com.ram.mandal.blesmartkit.data.model.Rashifal
import com.ram.mandal.blesmartkit.ui.components.ButtonComponents
import com.ram.mandal.blesmartkit.ui.components.TextComponents
import com.ram.mandal.blesmartkit.ui.components.TextComponentsWithMaxLine
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor
import com.ram.mandal.blesmartkit.util.bleBondState
import com.ram.mandal.blesmartkit.util.bleType


/**
 * Created by Ram Mandal on 25/01/2024
 * @System: Apple M1 Pro
 */


@Composable
fun NotificationLayout() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(minHeight = 50.dp)
            .background(AppThemeColor.yellow10)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(CircleShape)
                    .background(color = AppThemeColor.yellow50)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_error_red),
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .align(Alignment.Center)
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp, vertical = 0.dp)
            ) {
                TextComponents(
                    text = "From my understanding, this removes the the linting error, as the padding parameter is \"used\"",
                    typography = MaterialTheme.typography.bodySmall
                )
                ButtonComponents(
                    modifier = Modifier.align(Alignment.End),
                    color = AppThemeColor.white,
                    buttonText = "Click to check",
                    buttonBgColor = AppThemeColor.primary,
                    height = 35.dp,
                    verticalPadding = 2.dp
                ) {

                }
            }
        }
    }
}

@Composable
fun DateLayout() {
    val labelColor = AppThemeColor.primary
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextComponents(
                text = "आजको मिती ",
                color = labelColor,
                typography = MaterialTheme.typography.headlineSmall.copy(fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            TextComponents(
                text = "११ माघ २०८०, बिहिवार / 25 Jan 2024, Thursday",
                color = labelColor,
                typography = MaterialTheme.typography.headlineSmall.copy(fontSize = 12.sp)
            )
        }
    }
}

@Composable
fun MainCategory(news: List<MyMenuItem>, onItemClick: (MyMenuItem) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier
            .height(200.dp)
            .fillMaxHeight(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(news) { item ->
            MainMenuCard(
                item = item, modifier = Modifier.height(185.dp), onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun NewsList1(news: List<NewsItem>, onItemClick: (NewsItem) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppThemeColor.white)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextComponents(
                    text = "Featured news", typography = MaterialTheme.typography.headlineSmall
                )
                ButtonComponents(
                    modifier = Modifier,
                    buttonText = "View all",
                    buttonBgColor = AppThemeColor.grey5,
                    height = 40.dp,
                    verticalPadding = 2.dp
                ) {

                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(news) {
                    NewsRow(newsItem = it, onItemClick = onItemClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsRow(newsItem: NewsItem, onItemClick: (NewsItem) -> Unit) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppThemeColor.grey5),
        onClick = { onItemClick.invoke(newsItem) },
        modifier = Modifier
            .fillMaxWidth()
            .width(250.dp)
            .defaultMinSize(minWidth = 50.dp)
            .defaultMinSize(minHeight = 120.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppThemeColor.greyLight, //Card background color
        ),
        shape = RoundedCornerShape(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .width(100.dp)
                    .height(100.dp),
                model = newsItem.icon,
                placeholder = painterResource(id = R.drawable.ic_error_red),
                error = painterResource(id = R.drawable.ic_error_red),
                contentDescription = "News Image",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            TextComponentsWithMaxLine(
                text = newsItem.title,
                maxLines = 4,
                typography = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun NewsCardButtList(news: List<NewsItem>, onItemClick: (NewsItem) -> Unit) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextComponents(
                text = "Featured news", typography = MaterialTheme.typography.headlineSmall
            )

        }
        Spacer(modifier = Modifier.height(5.dp))
        LazyVerticalGrid(
            modifier = Modifier.height(200.dp),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(news) { item ->
                NewsCardButton(newsItem = item, onItemClick = onItemClick)
            }
        }
    }

}


@Composable
fun MainMenuCard(
    item: MyMenuItem, modifier: Modifier = Modifier, onItemClick: (MyMenuItem) -> Unit
) {
    Card(
        border = BorderStroke(width = 0.1.dp, color = AppThemeColor.primary.copy(alpha = 0.5f)),
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        onClick = { onItemClick.invoke(item) },
        colors = CardDefaults.cardColors(containerColor = AppThemeColor.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {

                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape)
                        .background(AppThemeColor.primary.copy(alpha = 0.2f))
                ) {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = "image details",
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(AppThemeColor.primary),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(25.dp)
                    )
                }
                TextComponentsWithMaxLine(
                    modifier = Modifier.padding(10.dp),
                    text = MenuConfig.getLabel(item.menuType),
                    typography = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCardButton(
    newsItem: NewsItem, modifier: Modifier = Modifier, onItemClick: (NewsItem) -> Unit
) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppThemeColor.grey1),
        onClick = { onItemClick.invoke(newsItem) },
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = AppThemeColor.greyLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), contentAlignment = Alignment.Center
        ) {
            Row {
                TextComponentsWithMaxLine(
                    text = newsItem.title, typography = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Composable
fun GoogleAddLayout() {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TapaikoRashifal(rashifal: Rashifal, onItemClick: () -> Unit) {
    Card(
        onClick = { onItemClick.invoke() },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppThemeColor.greyLight, //Card background color
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextComponents(
                text = "तपाईंको राशिफल ", typography = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(10.dp))
            TextComponentsWithMaxLine(
                text = rashifal.title, typography = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextComponents(
                text = rashifal.detail, typography = MaterialTheme.typography.bodyLarge
            )

        }
    }
}

/**
 * List of Radios
 */

@Composable
fun HomeRadioBlock(menuItems: List<MyMenuItem>, onItemClick: (MyMenuItem) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
            .background(AppThemeColor.white)
    ) {
        Column {
            TextComponents(
                modifier = Modifier.padding(16.dp, 2.dp),
                text = "FM Radio",
                typography = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(5.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(menuItems) {
                    HomeRadioItem(menuItem = it, onItemClick = onItemClick)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRadioItem(menuItem: MyMenuItem, onItemClick: (MyMenuItem) -> Unit) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppThemeColor.grey8),
        onClick = { onItemClick.invoke(menuItem) },
        modifier = Modifier
            .size(130.dp)
            .padding(2.dp)
            .defaultMinSize(130.dp),
        colors = CardDefaults.cardColors(containerColor = AppThemeColor.greyLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(42.dp),
                    model = menuItem.icon,
                    placeholder = painterResource(id = R.drawable.ic_error_red),
                    error = painterResource(id = R.drawable.ic_error_red),
                    contentDescription = "News Image",
                )
                Spacer(modifier = Modifier.height(5.dp))
                TextComponents(
                    text = menuItem.menuType.name, typography = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


/**
 * Popular Raadio List components
 */

@Composable
fun SecondaryMenuBlock(
    groupName: String = "Learning", menuItems: List<MyMenuItem>, onItemClick: (MyMenuItem) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
            .background(AppThemeColor.white)
    ) {
        Column {
            TextComponents(
                modifier = Modifier.padding(16.dp, 2.dp),
                text = groupName,
                typography = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(5.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(menuItems) {
                    SecondaryMenuItem(menuItem = it, onItemClick = onItemClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryMenuItem(menuItem: MyMenuItem, onItemClick: (MyMenuItem) -> Unit) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppThemeColor.grey8),
        onClick = { onItemClick.invoke(menuItem) },
        modifier = Modifier
            .size(130.dp)
            .padding(2.dp)
            .defaultMinSize(130.dp),
        colors = CardDefaults.cardColors(containerColor = AppThemeColor.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(42.dp),
                    model = menuItem.icon,
                    placeholder = painterResource(id = R.drawable.ic_error_red),
                    error = painterResource(id = R.drawable.ic_error_red),
                    contentDescription = "News Image",
                )
                Spacer(modifier = Modifier.height(5.dp))
                TextComponents(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = MenuConfig.getLabel(menuItem.menuType),
                    typography = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


/**
 * List of Videos
 */

@Composable
fun VideoBlock(video: List<MyVideo>, onItemClick: (MyVideo) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
            .background(AppThemeColor.white)
    ) {
        Column {
            TextComponents(
                modifier = Modifier.padding(16.dp, 2.dp),
                text = "Videos",
                typography = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(5.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(video) {
                    VideoItem(radio = it, onItemClick = onItemClick)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoItem(radio: MyVideo, onItemClick: (MyVideo) -> Unit) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppThemeColor.grey5),
        onClick = { onItemClick.invoke(radio) },
        modifier = Modifier
            .padding(2.dp)
            .width(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppThemeColor.white),

            ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    model = radio.icon,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_error_red),
                    error = painterResource(id = R.drawable.ic_error_red),
                    contentDescription = "Videos"
                )
                TextComponentsWithMaxLine(
                    maxLines = 1,
                    modifier = Modifier.padding(10.dp),
                    text = radio.name,
                    typography = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/**
 * Bottom News (Manoranjanaatmak news)
 */

@Composable
fun ManoranjanaatmakNews(news: List<NewsItem>, onItemClick: (NewsItem) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppThemeColor.white)
    ) {
        Column {
            TextComponents(
                text = "मनोरन्जनात्मक समाचार ",
                modifier = Modifier.padding(16.dp, 2.dp),
                typography = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(5.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(news) {
                    ManoranjanaatmakNewsRow(newsItem = it, onItemClick = onItemClick)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManoranjanaatmakNewsRow(newsItem: NewsItem, onItemClick: (NewsItem) -> Unit) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppThemeColor.grey8),
        modifier = Modifier
            .padding(4.dp)
            .width(250.dp)
            .height(260.dp),
        colors = CardDefaults.cardColors(containerColor = AppThemeColor.greyLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = { onItemClick.invoke(newsItem) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppThemeColor.white),

            ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(165.dp),
                    model = newsItem.icon,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_error_red),
                    error = painterResource(id = R.drawable.ic_error_red),
                    contentDescription = "Videos"
                )
                TextComponentsWithMaxLine(
                    modifier = Modifier.padding(10.dp),
                    text = newsItem.title,
                    typography = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Composable
fun BleCard(
    device: DiscoveredBleDevice,
    onItemClick: (DiscoveredBleDevice) -> Unit,
    onConnectClicked: () -> Unit
) {
    Card(
        border = BorderStroke(width = 1.dp, color = AppThemeColor.grey5),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = AppThemeColor.greyLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = { onItemClick.invoke(device) }) {
        Column(modifier = Modifier.padding(10.dp)) {
            //Bluetooth device name
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp),
                    painter = painterResource(R.drawable.ic_bluetooth),
                    tint = Color.Unspecified,
                    contentDescription = "Bluetooth",
                )
                TextComponents(
                    text = device.name,
                    modifier = Modifier.padding(4.dp, 0.dp),
                    color = AppThemeColor.black,
                    typography = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))

                ButtonComponents(
                    modifier = Modifier.padding(0.dp),
                    color = AppThemeColor.white,
                    buttonText = "connect",
                    buttonBgColor = AppThemeColor.primary,
                    height = 30.dp,
                    corner = 10,
                    verticalPadding = 0.dp
                ) { onConnectClicked.invoke() }
            }

            Spacer(modifier = Modifier.height(2.dp))

            //Bluetooth address and RSSI
            //MAC address : RSSI : -12
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp),
                    painter = painterResource(R.drawable.ic_address),
                    tint = Color.Unspecified,
                    contentDescription = "Address",
                )
                TextComponents(
                    text = device.address,
                    modifier = Modifier.padding(4.dp, 0.dp),
                    color = AppThemeColor.black,
                    typography = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_rssi),
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp),
                    tint = Color.Unspecified,
                    contentDescription = "RSSI",
                )
                TextComponents(
                    text = device.rssi.toString(),
                    modifier = Modifier.padding(4.dp, 0.dp),
                    color = AppThemeColor.black,
                    typography = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            //Bluetooth service UUIDs
            //Heart rate, temperature
            if (!device.serviceUUIDs.isEmpty()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        painter = painterResource(R.drawable.ic_rssi),
                        tint = Color.Unspecified,
                        contentDescription = "Service UUID",
                    )
                    for (uuid in device.serviceUUIDs) {
                        Log.v("ServiceUUID", uuid)
                        ButtonComponents(
                            modifier = Modifier.padding(0.dp),
                            color = AppThemeColor.grey5,
                            buttonText = uuid,
                            buttonBgColor = AppThemeColor.grey10,
                            height = 30.dp,
                            corner = 10,
                            verticalPadding = 0.dp
                        ) {}
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            //Bluetooth Type | Bond state
            Row(modifier = Modifier.fillMaxWidth()) {
                Row {
                    Icon(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        painter = painterResource(R.drawable.ic_device_type),
                        contentDescription = "BLE Type",
                        tint = AppThemeColor.black
                    )
                    TextComponents(
                        text = bleType(device.type),
                        modifier = Modifier.padding(4.dp, 0.dp),
                        color = AppThemeColor.black,
                        typography = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                Row {
                    Icon(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        painter = painterResource(R.drawable.ic_paired),
                        contentDescription = "Bond state",
                        tint = AppThemeColor.black
                    )
                    TextComponents(
                        text = bleBondState(device.bondState),
                        modifier = Modifier.padding(4.dp, 0.dp),
                        color = AppThemeColor.black,
                        typography = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

