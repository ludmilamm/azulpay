package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class BaseResponseRemoteModel<T>(@SerializedName("fields") val response: T)

data class BaseCollectionResponseRemoteModel<T>(@SerializedName("documents") val responseList: List<BaseResponseRemoteModel<T>>)

data class StringValue(@SerializedName("stringValue") val string: String)

data class DoubleValue(@SerializedName("doubleValue") val double: Double)