package com.rxjava.practice.model

import com.google.gson.annotations.SerializedName

data class Translation(
    @SerializedName("status") val status: Int,
    @SerializedName("content") val content: Content
) {
    data class Content(
        @SerializedName("from") val from: String,
        @SerializedName("to") val to: String,
        @SerializedName("vendor") val vendor: String,
        @SerializedName("out") val out: String,
        @SerializedName("errNo") val errorNum: String
    )
}