package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponseRemoteModel(@SerializedName("localId") val id: String,
                                    @SerializedName("idToken") val token: String,
                                    @SerializedName("refreshToken") val refreshToken: String)