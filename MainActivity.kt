package com.example.flaskdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sendtext = findViewById<EditText>(R.id.send_text_data)

        btnPost.setOnClickListener {
            val jsonObj = JsonObject()
            val senddata = sendtext.text.toString()
            jsonObj.addProperty("data", senddata)
            //  POST demo
            Api.service
                    .getVectors(jsonObj)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            println("---TTTT :: POST Throwable EXCEPTION:: " + t.message)
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                val msg = response.body()?.string()
                                Log.e("data",msg)
                                view_text.text = msg
                                Toast.makeText(applicationContext,"text entered is ${msg}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
        }

        btnGET.setOnClickListener {
            Api.service
                    .greetUser("user_name_")
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            println("---TTTT :: GET Throwable EXCEPTION:: " + t.message)
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                val msg = response.body()?.string()
                                println("---TTTT :: GET msg from server :: " + msg)

                                Toast.makeText(applicationContext,"user is {msg}", Toast.LENGTH_SHORT).show()
//                                log.e("data",msg.data)
                            }
                        }
                    })
        }
    }
}