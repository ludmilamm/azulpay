package br.com.azulpay.presentation.common.event

import br.com.azulpay.presentation.common.model.ErrorDialogDisplayModel
import br.com.azulpay.presentation.common.model.ErrorDisplayModel
import com.hadilq.liveevent.LiveEvent

open class SingleEvent<out T> {
    data class Success<out T>(val data: T) : SingleEvent<T>()
    data class Error<out T>(val error: ErrorDisplayModel?) : SingleEvent<T>()
    data class ErrorDialog<out T>(val error: ErrorDialogDisplayModel?) : SingleEvent<T>()
    class Loading<out T> : SingleEvent<T>()
    class DismissLoading<out T> : SingleEvent<T>()
}

fun <T> LiveEvent<SingleEvent<T>>.postSuccess(value: T) = postValue(SingleEvent.Success(value))

fun <T> LiveEvent<SingleEvent<T>>.postError(error: ErrorDisplayModel) = postValue(SingleEvent.Error(error))

fun <T> LiveEvent<SingleEvent<T>>.postErrorDialog(error: ErrorDialogDisplayModel) = postValue(SingleEvent.ErrorDialog(error))

fun <T> LiveEvent<SingleEvent<T>>.postLoading() = postValue(SingleEvent.Loading())

fun <T> LiveEvent<SingleEvent<T>>.postDismissLoading() = postValue(SingleEvent.DismissLoading())