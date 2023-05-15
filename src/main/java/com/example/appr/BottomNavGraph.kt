package com.example.appr

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appr.mainScreens.*
import java.util.prefs.Preferences

var isset = false

//val dataStore: DataStore<Preferences> by preferencesDataStore(name="settings")

@Composable
fun BottomNavGraph(navController:NavHostController){
    NavHost(
        navController=navController,
        startDestination=NavBarStuff.Home.route
    ){
        composable(route=NavBarStuff.Home.route){
            HomeScreen()
        }
        composable(route=NavBarStuff.Devices.route){
            DevicesScreen()
        }
        composable(route=NavBarStuff.Usage.route){
            UsageScreen()
        }
        composable(route=NavBarStuff.Usage2.route){
            UsageScreen2()
        }

    }
}