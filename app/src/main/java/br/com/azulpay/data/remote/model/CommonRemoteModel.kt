package br.com.azulpay.data.remote.model

import com.google.gson.annotations.SerializedName

data class FieldsRemoteModel<T>(@SerializedName("fields") val fields: T)

data class FieldsAndDateRemoteModel<T>(@SerializedName("fields") val fields: T,
                                       @SerializedName("createTime") val date: String)

data class DocumentsRemoteModel<T>(@SerializedName("documents") val documents: List<FieldsAndDateRemoteModel<T>>)

data class StringRemoteModel(@SerializedName("stringValue") val string: String)

data class DoubleRemoteValue(@SerializedName("doubleValue") val double: Double)

data class MapRemoteModel<T>(@SerializedName("mapValue") val map: FieldsRemoteModel<T>)