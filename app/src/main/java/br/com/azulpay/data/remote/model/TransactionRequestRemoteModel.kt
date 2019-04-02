package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TransactionRequestRemoteModel(@SerializedName("fields") val fields: Transaction) {

    data class Transaction(@SerializedName("value") val value: DoubleRemoteValue,
                           @SerializedName("to") val to: MapRemoteModel<User>,
                           @SerializedName("from") val from: MapRemoteModel<User>)

    data class User(@SerializedName("id") val id: StringRemoteModel,
                    @SerializedName("name") val name: StringRemoteModel,
                    @SerializedName("phone") val phone: StringRemoteModel,
                    @SerializedName("image") val image: StringRemoteModel)
}