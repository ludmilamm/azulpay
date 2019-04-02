package br.com.azulpay.presentation.common.event

import androidx.lifecycle.MutableLiveData
import br.com.azulpay.presentation.common.model.ErrorDialogDisplayModel
import br.com.azulpay.presentation.common.model.ErrorDisplayModel

open class StateEvent<out T> {
    data class Success<out T>(val data: T) : StateEvent<T>()
    data class Error<out T>(val error: ErrorDisplayModel?) : StateEvent<T>()
    data class ErrorDialog<out T>(val error: ErrorDialogDisplayModel?) : StateEvent<T>()
}

fun <T> MutableLiveData<StateEvent<T>>.postSuccess(value: T) {
    this.value = StateEvent.Success(value)
}

fun <T> MutableLiveData<StateEvent<T>>.postErrorDialog(error: ErrorDialogDisplayModel) {
    value = StateEvent.ErrorDialog(error)
}

fun <T> MutableLiveData<StateEvent<T>>.postError(error: ErrorDisplayModel) {
    value = StateEvent.Error(error)
}