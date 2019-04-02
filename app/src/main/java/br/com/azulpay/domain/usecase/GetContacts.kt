package br.com.azulpay.domain.usecase

import br.com.azulpay.common.di.BackgroundScheduler
import br.com.azulpay.common.di.MainScheduler
import br.com.azulpay.domain.datarepository.UserDataRepository
import br.com.azulpay.domain.model.User
import br.com.azulpay.domain.utility.Logger
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetContacts @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                      @MainScheduler postExecutionScheduler: Scheduler,
                                      logger: Logger,
                                      private val userDataRepository: UserDataRepository) : SingleUseCase<List<User>, Unit>(executorScheduler, postExecutionScheduler, logger) {

    override fun getRawSingle(params: Unit): Single<List<User>> {
        return Single.zip(userDataRepository.getAuthenticatedUser(),
                userDataRepository.getUsers(),
                BiFunction { user: User, users: List<User> -> users.filter { it.id != user.id } })
    }
}