package com.ram.mandal.blesmartkit.data.model

import android.os.Parcelable
import com.ram.mandal.blesmartkit.R
import com.ram.mandal.blesmartkit.ui.routes.AppRoutes
import kotlinx.parcelize.Parcelize


/**
 * Created by Ram Mandal on 30/01/2024
 * @System: Apple M1 Pro
 */
@Parcelize
data class NavigationItem(
    val id: EnumNavigationDrawerItem,
    val title: String,
    val icon: Int,
    val contentDesc: String,
    val route: String
): Parcelable

fun getNavigationItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            EnumNavigationDrawerItem.Home,
            "Home",
            R.drawable.ic_error_red,
            "",
            route = AppRoutes.Home.route
        ),
    )
}