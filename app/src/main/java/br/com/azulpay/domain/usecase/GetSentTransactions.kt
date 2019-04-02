package br.com.azulpay.domain.usecase

import br.com.azulpay.common.di.BackgroundScheduler
import br.com.azulpay.common.di.MainScheduler
import br.com.azulpay.domain.datarepository.TransactionDataRepository
import br.com.azulpay.domain.datarepository.UserDataRepository
import br.com.azulpay.domain.model.Transaction
import br.com.azulpay.domain.model.User
import br.com.azulpay.domain.utility.Logger
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetSentTransactions @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                              @MainScheduler postExecutionScheduler: Scheduler,
                                              logger: Logger,
                                              private val transactionDataRepository: TransactionDataRepository,
                                              private val userDataRepository: UserDataRepository) : SingleUseCase<List<Transaction>, Unit>(executorScheduler, postExecutionScheduler, logger) {

    override fun getRawSingle(params: Unit): Single<List<Transaction>> {
        return Single.zip(transactionDataRepository.getTransactions(),
                userDataRepository.getAuthenticatedUser(),
                BiFunction { transactions: List<Transaction>, user: User -> transactions.filter { it.from.id == user.id } })
    }
}