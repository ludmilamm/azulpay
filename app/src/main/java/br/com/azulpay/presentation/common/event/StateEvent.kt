package br.com.azulpay.presentation.common.event

import androidx.lifecycle.MutableLiveData
import br.com.azulpay.presentation.common.model.ErrorDialogDisplayModel

open class StateEvent<out T> {
    data class Success<out T>(val data: T) : StateEvent<T>()
    data class Error<out T>(val error: ErrorDialogDisplayModel?) : StateEvent<T>()
}

fun <T> MutableLiveData<StateEvent<T>>.postSuccess(value: T) = postValue(StateEvent.Success(value))

fun <T> MutableLiveData<StateEvent<T>>.postError(error: ErrorDialogDisplayModel) = postValue(StateEvent.Error(error))