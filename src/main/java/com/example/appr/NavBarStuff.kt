package com.example.appr
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
sealed class NavBarStuff(
    val route: String,
    val screenName: String,
    val pic: ImageVector
){
    object Home: NavBarStuff(
        route = "Home",
        screenName="Home",
        pic=Icons.Default.Home
    )

    object Devices: NavBarStuff(
        route = "Devices",
        screenName="Devices",
        pic=Icons.Default.Info
    )
    object Usage:NavBarStuff(
        route="Usage",
        screenName="Transmitted",
        pic=Icons.Default.ArrowBack
    )
    object Usage2: NavBarStuff(
        route = "Usage2",
        screenName="Received",
        pic=Icons.Default.ArrowForward
    )


}
