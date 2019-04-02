package br.com.azulpay.domain.usecase

import br.com.azulpay.common.EmptyFieldException
import br.com.azulpay.common.InvalidFieldException
import br.com.azulpay.common.di.BackgroundScheduler
import br.com.azulpay.common.di.MainScheduler
import br.com.azulpay.domain.utility.Logger
import io.reactivex.Completable
import io.reactivex.Scheduler
import java.math.BigDecimal
import javax.inject.Inject

class ValidateValueToSend @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                              @MainScheduler postExecutionScheduler: Scheduler,
                                              logger: Logger) : CompletableUseCase<BigDecimal?>(executorScheduler, postExecutionScheduler, logger) {

    override fun getRawCompletable(params: BigDecimal?): Completable {
        return params?.let {
            if (it <= BigDecimal.ZERO)
                Completable.error(EmptyFieldException())
            else
                Completable.complete()
        } ?: Completable.error(InvalidFieldException())
    }
}