package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequestRemoteModel(@SerializedName("refresh_token") val refreshToken: String,
                                          @SerializedName("grant_type") val grantType: String = "refresh_token")