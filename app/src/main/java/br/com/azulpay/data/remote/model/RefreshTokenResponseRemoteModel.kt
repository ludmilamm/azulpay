package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponseRemoteModel(@SerializedName("id_token") val token: String)