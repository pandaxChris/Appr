package com.example.appr.mainScreens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appr.classes.Users
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import okhttp3.RequestBody.Companion.toRequestBody

//var usr = Users(name="iPhone", ip="192.168.30.63", mac= "E0:6D:17:E2:C1:1F", contact="[hichris096@gmail.com,6466736817]")
//var usr2 = Users(name="Ubuntu Laptop", ip="192.168.30.51", mac= "B4:86:55:F4:B9:48", contact="[hichris096@gmail.com,6466736817]")




const val urlConn = "https://appr-jpfp5k3wma-ue.a.run.app/getConnected"
const val urlWait= "https://appr-jpfp5k3wma-ue.a.run.app/getWait"



@Composable
fun DevicesScreen(){
    var userList = remember{ mutableStateListOf<Users>() }
    var waitingList = remember{ mutableStateListOf<Users>() }
    var blockedList = remember{ mutableStateListOf<Users>()}

    val client = OkHttpClient()
    val request = Request.Builder().url(urlWait).build()
    val resp = client.newCall(request).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful){

                val gson = GsonBuilder().create()
                val packagesArray = gson.fromJson(response.body!!.string() , Array<Users>::class.java).toList()

                for(item in packagesArray){
                    var same = false
                    for(i in waitingList){
                        if (i.mac == item.mac){
                            same = true
                            break
                        }
                    }
                    if(same) break
                    waitingList.add(item)
                }
                //println(waitingList.size)
            }
        }
    })

    val client3 = OkHttpClient()
    val request3 = Request.Builder().url(urlConn).build()
    val resp3 = client3.newCall(request3).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful){

                val gson = GsonBuilder().create()
                val packagesArray = gson.fromJson(response.body!!.string() , Array<Users>::class.java).toList()

                for(item in packagesArray){
                    var same = false
                    for(i in userList){
                        if (i.mac == item.mac){
                            same = true
                            break
                        }
                    }
                    if(same) break
                    userList.add(item)
                }
                //println(userList.size)
            }
        }
    })

    Scaffold(backgroundColor=MaterialTheme.colors.primary) {

        Column(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                "Connected Devices",
                fontSize = 34.sp,
                fontWeight = FontWeight.SemiBold
            )
            if(userList.isEmpty()){
                Text(
                    "You currently have no connected Devices.",
                    fontStyle= FontStyle.Italic,
                    fontSize=32.sp
                )
            }else {
                for (x in userList) {
                    UserCard(us = x)
                }
            }
            Text(
                "Waiting Devices",
                fontSize = 34.sp,
                fontWeight = FontWeight.SemiBold
            )
            if(waitingList.isEmpty()){

                Text(
                    "There are no devices waiting.",
                    fontStyle= FontStyle.Italic,
                    fontSize=32.sp
                )
            }else {
                for (x in waitingList) {
                    WaitingCard(us = x)
                }
            }
            Text(
                "Blocked Devices",
                fontSize = 34.sp,
                fontWeight = FontWeight.SemiBold
            )
            if(blockedList.isEmpty()){

                Text(
                    "No blocked devices",
                    fontStyle= FontStyle.Italic,
                    fontSize=32.sp
                )
            }else {
                for (x in blockedList) {
                    WaitingCard(us = x)
                }
            }
        }

    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCard(us:Users){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .animateContentSize(
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
        ,
        shape= RoundedCornerShape(4.dp),
        onClick={expanded=!expanded}
    ){
        Column(
            modifier= Modifier
                .fillMaxWidth()
        ) {
            Row{
                Text(text=us.name,fontSize=24.sp)
            }
            if(expanded){
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ) {
                    Text("${us.ip} / ${us.mac}")
                }
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ){
                    Text("Contact: ${us.contact}")
                }
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ){
                    Text("UserAgent:${us.ua}")
                }
                Row{
                    Button(
                        colors=ButtonDefaults.buttonColors(backgroundColor=Color.Red),
                        onClick = {
                            val client = OkHttpClient()
                            val ip = "ip=${us.ip}"
                            val mac = "mac=${us.mac}"
                            val payload = "$ip&$mac".toRequestBody()
                            val request= Request.Builder()
                                .post(payload).addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .url("https://appr-jpfp5k3wma-ue.a.run.app/removeUser")
                                .build()

                            client.newCall(request).enqueue(object: Callback{
                                override fun onFailure(call: Call, e: IOException) {
                                    e.printStackTrace()
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    if(response.isSuccessful){
                                        if(response.body!!.string() == "1"){
                                            println("Removed")
                                        }
                                    }
                                }
                            })
                            //println(ip)
                            //println(mac)

                        },
                        modifier=Modifier.padding(start=150.dp,bottom=5.dp)
                    ) {
                        Text("Disconnect")
                    }
                    Button(
                        colors=ButtonDefaults.buttonColors(backgroundColor=Color.Red),
                        onClick = {
                            val client = OkHttpClient()
                            val ip = "ip=${us.ip}"
                            val mac = "mac=${us.mac}"
                            val payload = "$ip&$mac".toRequestBody()
                            val request= Request.Builder()
                                .post(payload).addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .url("https://appr-jpfp5k3wma-ue.a.run.app/blockUser")
                                .build()

                            client.newCall(request).enqueue(object: Callback{
                                override fun onFailure(call: Call, e: IOException) {
                                    e.printStackTrace()
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    if(response.isSuccessful){
                                        if(response.body!!.string() == "1"){
                                            println("Blocked")
                                        }
                                    }
                                }
                            })
                            //println(ip)
                            //println(mac)

                        },
                        modifier=Modifier.padding(start=25.dp,bottom=5.dp)
                    ) {
                        Text("Block")
                    }
                }

            }
        }


    }

}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WaitingCard(us:Users){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .animateContentSize(
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
        ,
        shape= RoundedCornerShape(4.dp),
        onClick={expanded=!expanded}
    ){
        Column(
            modifier= Modifier
                .fillMaxWidth()
        ) {
            Row{
                Text(text=us.name,fontSize=24.sp)
            }
            if(expanded){
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ) {
                    Text("${us.ip} / ${us.mac}")
                }
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ){
                    Text("Contact: ${us.contact}")
                }
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ){
                    Text("UserAgent:${us.ua}")
                }
                Row{
                    Button(
                        colors=ButtonDefaults.buttonColors(backgroundColor=Color.Green),
                        onClick = {
                                val client2 = OkHttpClient()
                                val ip = "ip=${us.ip}"
                                val mac = "mac=${us.mac}"
                                val payload = "$ip&$mac".toRequestBody()
                                val request= Request.Builder()
                                    .post(payload).addHeader("Content-Type", "application/x-www-form-urlencoded")
                                    .url("https://appr-jpfp5k3wma-ue.a.run.app/ownerApprove")
                                    .build()

                                client2.newCall(request).enqueue(object: Callback{
                                    override fun onFailure(call: Call, e: IOException) {
                                        e.printStackTrace()
                                    }

                                    override fun onResponse(call: Call, response: Response) {
                                        if(response.isSuccessful){
                                            if(response.body!!.string() == "1"){
                                                println("Approved")
                                            }
                                        }
                                    }
                                })
                            //println(ip)
                            //println(mac)

                         },
                        modifier=Modifier.padding(start=250.dp,bottom=5.dp)
                    ) {
                        Text("Approve")
                    }

                }
            }
        }


    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BlockedCard(us:Users){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .animateContentSize(
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
        ,
        shape= RoundedCornerShape(4.dp),
        onClick={expanded=!expanded}
    ){
        Column(
            modifier= Modifier
                .fillMaxWidth()
        ) {
            Row{
                Text(text=us.name,fontSize=24.sp)
            }
            if(expanded){
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ) {
                    Text("${us.ip} / ${us.mac}")
                }
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ){
                    Text("Contact: ${us.contact}")
                }
                Row(
                    modifier=Modifier.padding(top=2.dp)
                ){
                    Text("UserAgent:${us.ua}")
                }
                Row{
                    Button(
                        colors=ButtonDefaults.buttonColors(backgroundColor=Color.Green),
                        onClick = {
                            val client2 = OkHttpClient()
                            val ip = "ip=${us.ip}"
                            val mac = "mac=${us.mac}"
                            val payload = "$ip&$mac".toRequestBody()
                            val request= Request.Builder()
                                .post(payload).addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .url("https://appr-jpfp5k3wma-ue.a.run.app/unblock")
                                .build()

                            client2.newCall(request).enqueue(object: Callback{
                                override fun onFailure(call: Call, e: IOException) {
                                    e.printStackTrace()
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    if(response.isSuccessful){
                                        if(response.body!!.string() == "1"){
                                            println("Unblocked")
                                        }
                                    }
                                }
                            })
                            //println(ip)
                            //println(mac)

                        },
                        modifier=Modifier.padding(start=250.dp,bottom=5.dp)
                    ) {
                        Text("Unblock")
                    }
                }
            }
        }


    }
}


