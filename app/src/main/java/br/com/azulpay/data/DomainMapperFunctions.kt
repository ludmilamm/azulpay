package br.com.azulpay.data

import br.com.azulpay.data.remote.model.*
import br.com.azulpay.domain.model.TransactionRequest

fun TransactionRequest.toRemoteModel(fromId: String, fromName: String, fromPhone: String): TransactionRequestRemoteModel {
    val to = TransactionRequestRemoteModel.User(StringRemoteModel(id), StringRemoteModel(name), StringRemoteModel(phone))
    val from = TransactionRequestRemoteModel.User(StringRemoteModel(fromId), StringRemoteModel(fromName), StringRemoteModel(fromPhone))
    val transaction = TransactionRequestRemoteModel.Transaction(DoubleRemoteValue(value.toDouble()), MapRemoteModel(FieldsRemoteModel(to)), MapRemoteModel(FieldsRemoteModel(from)))
    return TransactionRequestRemoteModel(transaction)
}