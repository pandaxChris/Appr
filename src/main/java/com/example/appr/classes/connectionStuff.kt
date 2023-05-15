package com.example.appr.classes

import androidx.compose.ui.platform.LocalContext
import com.example.appr.MainNav
import okhttp3.*
import java.net.URL
import java.io.IOException
import okhttp3.RequestBody.Companion.toRequestBody

fun runGET(url: String) : Array<String>{
    var client: OkHttpClient = OkHttpClient()
    var res= ""
    var connected = true
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) connected = false
                else connected = true
                val resp = response.body!!.string()
                println("Got response: $resp")
                res = resp
            }

        }
    })


    return arrayOf(connected.toString(), res)
}

fun runPOST(url: String, payload: String) {
    var client : OkHttpClient = OkHttpClient()
    var res = ""
    var connected = true


    val request = Request.Builder().post(payload.toRequestBody()).url(url).build()
    //println(request.toString())
    println(request.body.toString())
    client.newCall(request).enqueue(object: Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }


        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    println("Failed POST request to: $url" )
                }
                val resp = response.body!!.string()
                println("Got response: $resp")
                res = resp
            }

        }
    })

}