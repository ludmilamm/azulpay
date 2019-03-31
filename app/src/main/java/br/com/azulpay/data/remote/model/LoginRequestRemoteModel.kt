package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginRequestRemoteModel(@SerializedName("email") val email: String = "amy@mail.com",
                                   @SerializedName("password") val password: String = "b99123",
                                   @SerializedName("returnSecureToken") val returnSecureToken: Boolean = true)