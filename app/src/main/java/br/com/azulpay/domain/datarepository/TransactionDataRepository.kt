package br.com.azulpay.domain.datarepository

import br.com.azulpay.domain.model.TransactionRequest
import io.reactivex.Completable

interface TransactionDataRepository {
    fun postTransaction(transactionRequest: TransactionRequest): Completable
}