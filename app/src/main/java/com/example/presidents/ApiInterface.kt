package com.example.presidents

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @Multipart
    @POST("predict")
    fun storePost(@Part image: MultipartBody.Part): Call<MyData>
}