package com.example.presidents

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyData(
    @SerializedName("prediction") @Expose
    private var prediction: String
)
