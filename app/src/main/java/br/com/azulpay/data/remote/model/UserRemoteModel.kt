package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserRemoteModel(@SerializedName("id") val id: StringValue,
                           @SerializedName("email") val email: StringValue,
                           @SerializedName("name") val name: StringValue,
                           @SerializedName("image") val image: StringValue,
                           @SerializedName("phone") val phone: StringValue,
                           @SerializedName("balance") val balance: DoubleValue)