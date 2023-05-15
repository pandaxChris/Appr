package com.example.appr

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController




@Composable
fun MainNav(){
    val navController= rememberNavController()
    Scaffold(
        bottomBar={BottomBar(navController=navController)}
    ){
        BottomNavGraph(navController=navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens=listOf(
        NavBarStuff.Home,
        NavBarStuff.Devices,
        NavBarStuff.Usage,
        NavBarStuff.Usage2
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination=navBackStackEntry?.destination

    BottomNavigation() {
        screens.forEach{ screen->
            addItem(
                screen=screen,
                currentDestintation = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun RowScope.addItem(
    screen:NavBarStuff,
    currentDestintation: NavDestination?,
    navController:NavHostController
){
    BottomNavigationItem(
        label={
            Text(text=screen.screenName)
        },
        icon={
            Icon(
                imageVector=screen.pic,
                contentDescription="Navigation Icon"
            )
        },
        selected=currentDestintation?.hierarchy?.any{ it.route==screen.route }==true,
        onClick={
            navController.navigate(screen.route)
        }
        //selectedContentColor=Color.YELLOW
    )
}
