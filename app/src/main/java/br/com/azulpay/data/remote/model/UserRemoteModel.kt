package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserRemoteModel(@SerializedName("id") val id: StringRemoteModel,
                           @SerializedName("email") val email: StringRemoteModel,
                           @SerializedName("name") val name: StringRemoteModel,
                           @SerializedName("image") val image: StringRemoteModel,
                           @SerializedName("phone") val phone: StringRemoteModel,
                           @SerializedName("balance") val balance: DoubleRemoteValue)