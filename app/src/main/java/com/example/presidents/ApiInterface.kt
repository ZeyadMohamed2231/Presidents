package com.example.presidents

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.File

interface ApiInterface {
    @POST("predict")
    fun storePost(@Body map:HashMap<String, String>): Call<MyData>
}