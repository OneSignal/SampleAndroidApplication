package com.example.sampleapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private var txtString: TextView? = null
    private var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Example of a GET HTTP request using OkHttp
     */
    fun getExample() {
        val request = Request.Builder()
            .url("https://publicobject.com/helloworld.txt")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { }

            override fun onResponse(call: Call, response: Response) {
                for ((name, value) in response.headers) {
                    println("$name: $value")
                }
                val responseString = response.body!!.string()
                println(responseString)
            }
        })
    }

    /**
     * Example of a Patch HTTP request using OkHttp
     */
    fun patchExample() {
        val payload = """
            {
                "name": "alice",
                "properties": {
                    "country": "US",
                    "language": "en"
                }
            }
        """.trimIndent()

        val request = Request.Builder()
            .url("https://api.github.com/markdown/raw")
            .patch(payload.toRequestBody())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { }

            override fun onResponse(call: Call, response: Response) {
                for ((name, value) in response.headers) {
                    println("$name: $value")
                }
                val responseString = response.body!!.string()
                println(responseString)
            }
        })
    }
}