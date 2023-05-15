package com.example.appr.mainScreens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.appr.classes.Users
import okhttp3.*
import java.io.IOException


@Composable
fun HomeScreen() {

    var connec by remember {mutableStateOf(false)}

    val url = "https://appr-jpfp5k3wma-ue.a.run.app/getAlive"

    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val resp = client.newCall(request).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful && response.body!!.string() != "False"){
                connec=true
                println(connec.toString())
            }
        }
    })


    var ip by remember{ mutableStateOf("")}
    var cl: OkHttpClient = OkHttpClient()

    val req = Request.Builder().url("https://appr-jpfp5k3wma-ue.a.run.app/getRtrIP").build()

    val res = client.newCall(req).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful){
                connec=true
                //println(connec.toString())
                ip = response.body!!.string()
               // println(ip)
            }
        }
    })

    var userConn by  remember{ mutableStateOf("0") }
    var waitingList by remember{ mutableStateOf("0") }

    val req2 = Request.Builder().url("https://appr-jpfp5k3wma-ue.a.run.app/getSizes").build()

    val res2 = client.newCall(req2).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful){
                var sizes=response.body!!.string()
                //println("Sizes:$sizes")
                userConn = sizes.split(" ")[1]
                waitingList=sizes.split(" ")[0]

            }
        }
    })



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*
        |               Home                          |
        |               Status                        |
        |             IP address                      |
        |                                             |
        |                                             |
        |           Connected devices:  #             |

     */

        Text(
            text = "Appr",
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold
        )


        if (!connec) {
            Text(
                text = "Not connected",
                color = Color.Red,
                fontSize = MaterialTheme.typography.h6.fontSize
            )
        } else {
            Text(
                text = "Connected",
                color = Color.Green,
                fontSize = MaterialTheme.typography.h6.fontSize
            )
        }

        Text(
            text = ip,
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h6.fontSize,
        )
        Text(
            text = "Number of users waiting: $waitingList",
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h6.fontSize,
        )
        Text(
            text = "Number of users connected: $userConn",
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h6.fontSize,
        )


    }
}

