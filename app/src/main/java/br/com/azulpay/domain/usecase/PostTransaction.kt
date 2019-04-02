package br.com.azulpay.domain.usecase

import br.com.azulpay.common.di.BackgroundScheduler
import br.com.azulpay.common.di.MainScheduler
import br.com.azulpay.domain.datarepository.TransactionDataRepository
import br.com.azulpay.domain.model.TransactionRequest
import br.com.azulpay.domain.utility.Logger
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class PostTransaction @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                          @MainScheduler postExecutionScheduler: Scheduler,
                                          logger: Logger,
                                          private val transactionDataRepository: TransactionDataRepository): CompletableUseCase<TransactionRequest>(executorScheduler, postExecutionScheduler, logger) {

    override fun getRawCompletable(params: TransactionRequest): Completable = transactionDataRepository.postTransaction(params)
}