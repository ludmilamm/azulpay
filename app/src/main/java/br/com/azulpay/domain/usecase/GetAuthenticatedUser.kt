package br.com.azulpay.domain.usecase

import br.com.azulpay.common.di.BackgroundScheduler
import br.com.azulpay.common.di.MainScheduler
import br.com.azulpay.domain.datarepository.UserDataRepository
import br.com.azulpay.domain.model.User
import br.com.azulpay.domain.utility.Logger
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetAuthenticatedUser @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                               @MainScheduler postExecutionScheduler: Scheduler,
                                               logger: Logger,
                                               private val userDataRepository: UserDataRepository) : SingleUseCase<User, Unit>(executorScheduler, postExecutionScheduler, logger) {

    override fun getRawSingle(params: Unit): Single<User> = userDataRepository.getAuthenticatedUser()
}