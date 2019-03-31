package br.com.azulpay.domain.datarepository

import br.com.azulpay.domain.model.Transaction
import br.com.azulpay.domain.model.TransactionRequest
import io.reactivex.Completable
import io.reactivex.Single

interface TransactionDataRepository {
    fun postTransaction(transaction: TransactionRequest): Completable
    fun getTransactions(): Single<List<Transaction>>
}