package br.com.azulpay.data.repository

import br.com.azulpay.data.cache.datasource.UserCacheDataSource
import br.com.azulpay.data.remote.datasource.TransactionRemoteDataSource
import br.com.azulpay.data.toRemoteModel
import br.com.azulpay.domain.datarepository.TransactionDataRepository
import br.com.azulpay.domain.model.TransactionRequest
import io.reactivex.Completable
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionRemoteDataSource: TransactionRemoteDataSource,
                                                private val userCacheDataSource: UserCacheDataSource) : TransactionDataRepository {

    override fun postTransaction(transactionRequest: TransactionRequest): Completable {
        return userCacheDataSource.getUser().flatMapCompletable {
            transactionRemoteDataSource.postTransaction(transactionRequest.toRemoteModel(it.id, it.name, it.phone, it.image)).ignoreElement()
        }
    }
}