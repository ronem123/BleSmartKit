package com.ram.mandal.blesmartkit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ram.mandal.blesmartkit.data.model.getNavigationItems
import com.ram.mandal.blesmartkit.data.network.ApiService
import com.ram.mandal.blesmartkit.ui.components.NavigationDrawerBody
import com.ram.mandal.blesmartkit.ui.components.NavigationDrawerHeader
import com.ram.mandal.blesmartkit.ui.routes.NavigationRoutes
import com.ram.mandal.blesmartkit.ui.screens.devicedetails.DeviceDetailScreen
import com.ram.mandal.blesmartkit.ui.screens.home.HomeScreen
import com.ram.mandal.blesmartkit.ui.theme.AppThemeColor
import com.ram.mandal.blesmartkit.ui.theme.BleSmartKitTheme
import com.ram.mandal.blesmartkit.util.MyIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BleSmartKitTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerHeader()
                NavigationDrawerBody(
                    navigationItems = getNavigationItems()
                ) { item ->
                    coroutineScope.launch {
                        coroutineScope.launch {
                            drawerState.close()
                            val intent = Intent(context, NavigateActivity::class.java)
                            intent.putExtra(MyIntent.navigationDrawer, item)
                            Log.v("TAAG", "Route : ${item.route}")
                            intent.putExtra(MyIntent.route, item.route)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(context.getString(R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AppThemeColor.primary,
                        titleContentColor = AppThemeColor.white,
                        navigationIconContentColor = AppThemeColor.white
                    )
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.home,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = NavigationRoutes.home) {
                    HomeScreen(navController = navController)
                }
                composable(
                    route = "device/{address}/{name}",
                    arguments = listOf(
                        navArgument("address") { type = NavType.StringType },
                        navArgument("name") { type = NavType.StringType }
                    )
                ) {
                    DeviceDetailScreen()
                }
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(NavigationRoutes.home, R.drawable.ic_home),
        BottomNavItem(NavigationRoutes.dastoor, R.drawable.ic_home),
        BottomNavItem(NavigationRoutes.fines, R.drawable.ic_home),
    )

    NavigationBar(
        containerColor = AppThemeColor.primary
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = currentDestination?.route == item.route,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.route,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.route,
                        color = if (currentDestination?.route == item.route) AppThemeColor.white else AppThemeColor.tabUnSelectedIcon
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = AppThemeColor.tabSelectedBG,
                    selectedIconColor = AppThemeColor.tabSelectedIcon,
                    selectedTextColor = AppThemeColor.tabSelectedIcon,
                    unselectedIconColor = AppThemeColor.tabUnSelectedIcon,
                    unselectedTextColor = AppThemeColor.tabUnSelectedIcon,
                )
            )
        }
    }
}

data class BottomNavItem(val route: String, val icon: Int)


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BleSmartKitTheme {
        Greeting("Android")
    }
}