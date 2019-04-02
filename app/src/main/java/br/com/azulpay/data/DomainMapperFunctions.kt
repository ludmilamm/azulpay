package br.com.azulpay.data

import br.com.azulpay.data.remote.model.*
import br.com.azulpay.domain.model.TransactionRequest

fun TransactionRequest.toRemoteModel(fromUserId: String, fromUserName: String, fromUserPhone: String, fromUserImage: String): TransactionRequestRemoteModel {
    val to = TransactionRequestRemoteModel.User(StringRemoteModel(toUserId), StringRemoteModel(toUserName), StringRemoteModel(toUserPhone), StringRemoteModel(toUserImage))
    val from = TransactionRequestRemoteModel.User(StringRemoteModel(fromUserId), StringRemoteModel(fromUserName), StringRemoteModel(fromUserPhone), StringRemoteModel(fromUserImage))
    val transaction = TransactionRequestRemoteModel.Transaction(DoubleRemoteValue(value.toDouble()), MapRemoteModel(FieldsRemoteModel(to)), MapRemoteModel(FieldsRemoteModel(from)))
    return TransactionRequestRemoteModel(transaction)
}