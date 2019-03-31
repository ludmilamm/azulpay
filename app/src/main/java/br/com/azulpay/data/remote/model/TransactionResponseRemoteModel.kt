package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class TransactionResponseRemoteModel(@SerializedName("to") val to: MapRemoteModel<User>,
                                          @SerializedName("from") val from: MapRemoteModel<User>,
                                          @SerializedName("value") val value: DoubleRemoteValue) {

    data class User(@SerializedName("id") val id: StringRemoteModel,
                    @SerializedName("name") val name: StringRemoteModel,
                    @SerializedName("image") val image: StringRemoteModel,
                    @SerializedName("phone") val phone: StringRemoteModel)
}